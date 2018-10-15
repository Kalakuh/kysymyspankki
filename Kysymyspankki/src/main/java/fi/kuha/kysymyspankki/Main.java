package fi.kuha.kysymyspankki;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
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
        KysymysDao kysymysDao = new KysymysDao(db);
        VastausvaihtoehtoDao vaihtoehtoDao = new VastausvaihtoehtoDao(db);
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            List<Kurssi> kurssit = kurssiDao.findAll();
            HashMap<Integer, Kurssi> kurssiTaulu = new HashMap<>();
            for (Kurssi kurssi : kurssit) {
                kurssiTaulu.put(kurssi.getId(), kurssi);
            }
            
            
            List<Aihe> aiheet = aiheDao.findAll();
            HashMap<Integer, Aihe> aiheTaulu = new HashMap<>();
            for (Aihe aihe : aiheet) {
                kurssiTaulu.get(aihe.getKurssi()).getAiheet().add(aihe);
                aiheTaulu.put(aihe.getId(), aihe);
            }
            
            List<Kysymys> kysymykset = kysymysDao.findAll();
            for (Kysymys kysymys : kysymykset) {
                aiheTaulu.get(kysymys.getAiheId()).getKysymykset().add(kysymys);
            }
            
            map.put("kurssit", kurssit);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/q/:qid/", (req, res) -> {
            HashMap map = new HashMap<>();
            int kysymysId = Integer.parseInt(req.params(":qid"));
            
            Kysymys kysymys = kysymysDao.findOne(kysymysId);
            
            Aihe aihe = aiheDao.findOne(kysymys.getAiheId());
            
            Kurssi kurssi = kurssiDao.findOne(aihe.getKurssi());
            
            List<Vastausvaihtoehto> vvs = vaihtoehtoDao.findAll();
            for (Vastausvaihtoehto vv : vvs) {
                if (vv.getKysymysId() == kysymysId) {
                    kysymys.getVaihtoehdot().add(vv);
                }
            }
            
            map.put("kurssi", kurssi);
            map.put("aihe", aihe);
            map.put("kysymys", kysymys);
            
            return new ModelAndView(map, "question");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/q/:qid/", (req, res) -> {
            int kysymysId = Integer.parseInt(req.params(":qid"));
            Vastausvaihtoehto vv = new Vastausvaihtoehto();
            vv.setKysymysId(kysymysId);
            vv.setTeksti(req.queryParams("teksti"));
            vv.setOikein("on".equals(req.queryParams("tosi")));
            vaihtoehtoDao.saveOrUpdate(vv);
            
            res.redirect("/q/" + kysymysId + "/");
            return "";
        });
        
        Spark.get("/q/:qid/delete/:oid/", (req, res) -> {
            int kysymysId = Integer.parseInt(req.params(":qid"));
            int vaihtoehtoId = Integer.parseInt(req.params(":oid"));
            
            vaihtoehtoDao.delete(vaihtoehtoId);
            
            res.redirect("/q/" + kysymysId + "/");
            return "";
        });
        
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
                ps.setInt(2, kurssiId);
                if (!aiheDao.resultSetNotEmpty(ps)) {
                    Aihe obj = new Aihe();
                    obj.setNimi(aihe);
                    obj.setKurssi(kurssiId);
                    aiheDao.saveOrUpdate(obj);
                }
            }
            int aiheId = aiheDao.findIdByColumnValues(Arrays.asList("nimi", "kurssi_id"), Arrays.asList(aihe, "" + kurssiId), Arrays.asList(true, false));
            
            {
                PreparedStatement ps = db.getConnection().prepareStatement("SELECT * FROM Kysymys WHERE teksti = ? AND aihe_id = ?;");
                ps.setString(1, teksti);
                ps.setInt(2, aiheId);
                if (!kysymysDao.resultSetNotEmpty(ps)) {
                    Kysymys obj = new Kysymys();
                    obj.setTeksti(teksti);
                    obj.setAiheId(aiheId);
                    kysymysDao.saveOrUpdate(obj);
                }
            }
            
            res.redirect("/");
            return "";
        });
        
        Spark.post("/delete", (req, res) -> {
            Integer id = Integer.parseInt(req.queryParams("kysymysId"));
            kysymysDao.delete(id);
            
            res.redirect("/");
            return "";
        });
    }
}
