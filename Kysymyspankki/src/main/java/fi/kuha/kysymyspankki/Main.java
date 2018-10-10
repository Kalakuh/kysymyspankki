package fi.kuha.kysymyspankki;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Database db = new Database("jdbc:sqlite:kysymyspankki.db");
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }
}
