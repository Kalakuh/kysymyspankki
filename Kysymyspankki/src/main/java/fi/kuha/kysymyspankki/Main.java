package fi.kuha.kysymyspankki;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Database db = new Database("jdbc:sqlite:kysymyspankki.db");
        KurssiDao kurssiDao = new KurssiDao(db);
        AiheDao aiheDao = new AiheDao(db);
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Kurssi> kurssit = kurssiDao.findAll();
            
            HashMap<Integer, Kurssi> kurssiTaulu = new HashMap<>();
            for (Kurssi kurssi : kurssit) {
                kurssiTaulu.put(kurssi.getId(), kurssi);
            }
            
            List<Aihe> aiheet = aiheDao.findAll();
            for (Aihe aihe : aiheet) {
                kurssiTaulu.get(aihe.getKurssi()).getAiheet().add(aihe);
            }
            
            map.put("kurssit", kurssit);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/add", (req, res) -> {
            HashMap map = new HashMap<>();
            String kurssi = req.queryParams("kurssi");
            String aihe = req.queryParams("aihe");
            String teksti = req.queryParams("kysymysteksti");
            {
                PreparedStatement ps = db.getConnection().prepareStatement("SELECT * FROM Kurssi WHERE nimi = ?;");
                ps.setString(1, kurssi);
                if (!kurssiDao.resultSetNotEmpty(ps)) {
                    Kurssi obj = new Kurssi();
                    obj.setNimi(kurssi);
                    kurssiDao.saveOrUpdate(obj);
                }
            }
            int kurssiId = kurssiDao.findIdByColumnValue("nimi", kurssi);
            {
                PreparedStatement ps = db.getConnection().prepareStatement("SELECT * FROM Aihe WHERE nimi = ? AND kurssi_id = ?;");
                ps.setString(1, aihe);
                ps.setInt(1, kurssiId);
                if (!aiheDao.resultSetNotEmpty(ps)) {
                    Aihe obj = new Aihe();
                    obj.setNimi(aihe);
                    obj.setKurssi(kurssiId);
                    aiheDao.saveOrUpdate(obj);
                }
            }
            //int aiheId = aiheDao.findIdByColumnValue("nimi", kurssi);
            
            res.redirect("/");
            return "";
        });
    }
}
