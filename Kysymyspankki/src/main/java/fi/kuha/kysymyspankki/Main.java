package fi.kuha.kysymyspankki;

import spark.Spark;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Database db = new Database("jdbc:sqlite:kysymyspankki.db");
        
        Spark.get("/", (req, res) -> {
            return "Hei maailma!";
        });
    }
}
