package com.festivalcompany.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.festivalcompany.model.Company;
import com.festivalcompany.model.Comment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirestoreService {

    private final Firestore db;

    public FirestoreService() {
        try {
            FileInputStream serviceAccount = new FileInputStream("path/to/your/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            throw new RuntimeException("Error initializing Firestore", e);
        }
    }

    public List<Company> getCompaniesByFestival(String festivalName) {
        try {
            QuerySnapshot querySnapshot = db.collection("companies")
                    .whereEqualTo("festivalName", festivalName)
                    .get()
                    .get();
            List<Company> companies = new ArrayList<>();
            for (QueryDocumentSnapshot document : querySnapshot) {
                companies.add(documentToCompany(document));
            }
            return companies;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error fetching companies", e);
        }
    }

    public void updateCompany(Company company) {
        try {
            db.collection("companies").document(company.getId()).set(companyToMap(company)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error updating company", e);
        }
    }

    public void deleteCompany(String companyId) {
        try {
            db.collection("companies").document(companyId).delete().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error deleting company", e);
        }
    }

    public void addComment(String companyId, String commentText) {
        try {
            DocumentReference docRef = db.collection("companies").document(companyId);
            Comment newComment = new Comment("Current User", commentText, new Date());
            docRef.update("comments", FieldValue.arrayUnion(commentToMap(newComment))).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error adding comment", e);
        }
    }

    private Company documentToCompany(DocumentSnapshot document) {
        Company company = new Company();
        company.setId(document.getId());
        company.setName(document.getString("name"));
        company.setProgramName(document.getString("programName"));
        company.setLastModified(document.getDate("lastModified"));

        List<String> equipmentSNs = (List<String>) document.get("equipmentSNs");
        if (equipmentSNs != null) {
            company.setEquipmentSNs(equipmentSNs);
        }

        company.setEloszto(Boolean.TRUE.equals(document.getBoolean("eloszto")));
        company.setAram(Boolean.TRUE.equals(document.getBoolean("aram")));
        company.setHalozat(Boolean.TRUE.equals(document.getBoolean("halozat")));
        company.setPtg(Boolean.TRUE.equals(document.getBoolean("PTG")));
        company.setSzoftver(Boolean.TRUE.equals(document.getBoolean("szoftver")));
        company.setParam(Boolean.TRUE.equals(document.getBoolean("param")));
        company.setHelyszin(Boolean.TRUE.equals(document.getBoolean("helyszin")));
        company.setTelepites(document.getString("telepites"));
        company.setFelderites(document.getString("felderites"));
        company.setDistributor(Boolean.TRUE.equals(document.getBoolean("distributor")));
        company.setElectricity(Boolean.TRUE.equals(document.getBoolean("electricity")));
        company.setNetwork(Boolean.TRUE.equals(document.getBoolean("network")));
        company.setSoftware(Boolean.TRUE.equals(document.getBoolean("software")));
        company.setParameter(Boolean.TRUE.equals(document.getBoolean("parameter")));
        company.setLocation(Boolean.TRUE.equals(document.getBoolean("location")));
        company.setDismantlingStatus(document.getString("dismantlingStatus"));
        company.setPackagingStatus(document.getString("packagingStatus"));
        company.setBaseDismantling(Boolean.TRUE.equals(document.getBoolean("baseDismantling")));

        List<Map<String, Object>> commentsData = (List<Map<String, Object>>) document.get("comments");
        if (commentsData != null) {
            List<Comment> comments = new ArrayList<>();
            for (Map<String, Object> commentData : commentsData) {
                comments.add(mapToComment(commentData));
            }
            company.setComments(comments);
        }

        return company;
    }

    private Map<String, Object> companyToMap(Company company) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", company.getName());
        data.put("programName", company.getProgramName());
        data.put("lastModified", company.getLastModified());
        data.put("equipmentSNs", company.getEquipmentSNs());
        data.put("eloszto", company.isEloszto());
        data.put("aram", company.isAram());
        data.put("halozat", company.isHalozat());
        data.put("PTG", company.isPtg());
        data.put("szoftver", company.isSzoftver());
        data.put("param", company.isParam());
        data.put("helyszin", company.isHelyszin());
        data.put("telepites", company.getTelepites());
        data.put("felderites", company.getFelderites());
        data.put("distributor", company.isDistributor());
        data.put("electricity", company.isElectricity());
        data.put("network", company.isNetwork());
        data.put("software", company.isSoftware());
        data.put("parameter", company.isParameter());
        data.put("location", company.isLocation());
        data.put("dismantlingStatus", company.getDismantlingStatus());
        data.put("packagingStatus", company.getPackagingStatus());
        data.put("baseDismantling", company.isBaseDismantling());

        List<Map<String, Object>> commentsData = new ArrayList<>();
        for (Comment comment : company.getComments()) {
            commentsData.add(commentToMap(comment));
        }
        data.put("comments", commentsData);

        return data;
    }

    private Map<String, Object> commentToMap(Comment comment) {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("author", comment.getAuthor());
        commentData.put("text", comment.getText());
        commentData.put("timestamp", com.google.cloud.Timestamp.of(comment.getTimestamp()));
        return commentData;
    }

    private Comment mapToComment(Map<String, Object> commentData) {
        return new Comment(
                (String) commentData.get("author"),
                (String) commentData.get("text"),
                ((com.google.cloud.Timestamp) commentData.get("timestamp")).toDate()
        );
    }
}