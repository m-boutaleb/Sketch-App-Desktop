package ch.supsi.pss.repository;

import ch.supsi.pss.model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import static ch.supsi.pss.utils.FileUtils.METADATA_EXTENSION;
import static ch.supsi.pss.utils.FileUtils.SKETCH_EXTENSION;

public class SketchRepository {
    private static SketchRepository instance;

    private SketchRepository(){
    }

    public static SketchRepository getInstance() {
        if(instance==null)
            instance= new SketchRepository();
        return instance;
    }

    public Set<Sketch> getAllSketches(final String prefPathDir, final SketchSerializer sketchSerializer) {
        final Set<Sketch> allLoadedSketch=new HashSet<>();
        loadAllSketchData(prefPathDir ,sketchSerializer, allLoadedSketch);
        return allLoadedSketch;
    }

    public Sketch findSketchByUUID(final UUID uuidToFind ,final Set<Sketch> allSketches){
        final Predicate<Sketch> sameUUID=u->u.getUUID().equals(uuidToFind);
        return allSketches.stream().filter(sameUUID::test).findFirst().orElse(null);
    }

    private void loadAllSktFiles(final String prefPathDir, final Sketch existingSketch, final SketchSerializer sketchSerializer, final Set<Sketch> allSketches, final String... sktFiles) {
        for(final var uuid: sktFiles)
            try (final FileInputStream inputStream= new FileInputStream(prefPathDir+File.separator+uuid)) {
                byte[] image=inputStream.readAllBytes();
                if(existingSketch!=null){
                    existingSketch.setImage(image);
                    return;
                }
                findSketchByUUID(UUID.fromString(uuid.replace(".skt", "")), allSketches).setImage(image);
            } catch (IOException e) {
                PssLogger.getInstance().error("ERROR WHILE READING IMAGE FILE...", getClass());
            }
    }

    private void loadAllMtdFiles(final String prefPathDir,Sketch existingSketch, final SketchSerializer sketchSerializer, final Set<Sketch> allSketches, final String... mtdFiles) {
        for(final var uuid: mtdFiles){
            try (FileReader reader = new FileReader(prefPathDir+File.separator+uuid)) {
                final Sketch sketch=sketchSerializer.createSketchByJSON((JSONObject) new JSONParser().parse(reader));
                if(existingSketch!=null){
                    existingSketch.copyOf(sketch);
                    return;
                }
                allSketches.add(sketch);
            } catch (IOException e) {
                PssLogger.getInstance().error("ERROR WHILE READING JSON FILE...", getClass());
            } catch (ParseException e) {
                PssLogger.getInstance().error("ERROR WHILE PARSING JSON FILES...", getClass());
            }
        }
    }

    private boolean loadAllSketchData(final String prefPathDir, final SketchSerializer sketchSerializer,final Set<Sketch> allSketches){
        PssLogger.getInstance().info("READING ALL DATA...", getClass());
        final GenericExtensionFilter filterBySketch= new GenericExtensionFilter(SKETCH_EXTENSION);
        final GenericExtensionFilter filterByMetadata= new GenericExtensionFilter(METADATA_EXTENSION);
        if(prefPathDir==null)
            return false;
        final File dir= new File(prefPathDir);
        if(!dir.isDirectory()) {
            PssLogger.getInstance().error("DIRECTORY " + prefPathDir + " DOES NOT EXISTS...", getClass());
            return false;
        }
        //lista tutte i nomi dei file .skt
        final String sktFiles[]=dir.list(filterBySketch);
        //lista tutte i nomi dei file .mtd
        final String mtdFiles[]=dir.list(filterByMetadata);
        if(mtdFiles.length==0 && sktFiles.length==0) {
            PssLogger.getInstance().warn("NO FILES FOUND", getClass());
            return true;
        }else if(mtdFiles.length!=sktFiles.length)
            PssLogger.getInstance().error("THERE ARE SOME SKETCHES/METADATA WITH NO METADATA/SKETCHES INFO", getClass());

        loadAllMtdFiles(prefPathDir,null,sketchSerializer, allSketches,mtdFiles);
        loadAllSktFiles(prefPathDir,null,sketchSerializer, allSketches,sktFiles);
        PssLogger.getInstance().info("ALL SKETCHES LOADED WITH SUCCESS", getClass());
        return true;
    }

    public boolean saveSketchMetadata(final JSONObject sketch, final String prefPathDir, final String uuid) {
        PssLogger.getInstance().info("SAVING SKETCH METADATA...", getClass());
        try (FileWriter file = new FileWriter(prefPathDir + File.separator + uuid + METADATA_EXTENSION)) {
            file.write(sketch.toString());
            file.flush();
            return true;
        } catch (IOException e) {
            PssLogger.getInstance().error("ERROR WHILE WRITING METADATA...", getClass());
        }
        return false;
    }

    public boolean saveSketchImage(final byte[] image, final String prefPathDir, final String uuid) {
        PssLogger.getInstance().info("SAVING SKETCH IMAGE...", getClass());
        if (image == null || uuid == null) {
            PssLogger.getInstance().error(new IllegalArgumentException("IMAGE CANNOT BE NULL"), getClass());
            return false;
        }
        try (final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(prefPathDir+ File.separator + uuid + SKETCH_EXTENSION))) {
            ByteArrayInputStream bais=new ByteArrayInputStream(image);
            BufferedImage bufferedImage= ImageIO.read(bais);
            ImageIO.write(bufferedImage, "png", out);
            return true;
        } catch (IOException e) {
            PssLogger.getInstance().error("ERROR WHILE WRITING IMAGE FILE...", getClass());
        }
        return false;
    }

    public UUID getLastSavedSketchUUID(final Set<Sketch> allSketches) {
        final var copy=new ArrayList<>(allSketches);
        Collections.sort(copy, new SketchDateComparator());
        return copy.get(0).getUUID();
    }

    public Sketch openSketch(final String path, final String sketchName, final SketchSerializer sketchSerializer) {
        Sketch existingSketch=new Sketch();
        var index= sketchName.lastIndexOf(".");
        loadAllMtdFiles(path,existingSketch, sketchSerializer, null, sketchName.substring(0, index).concat(METADATA_EXTENSION));
        loadAllSktFiles(path, existingSketch,sketchSerializer, null, sketchName);
        return existingSketch;
    }
}
