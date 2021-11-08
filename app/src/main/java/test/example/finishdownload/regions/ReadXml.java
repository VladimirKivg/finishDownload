package test.example.finishdownload.regions;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ReadXml {

    private ArrayList<Region> regions = new ArrayList<>();

    public ReadXml(XmlPullParser parser) {
        readXml(parser);
    }

    private void readXml(XmlPullParser parser) {

        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    switch (parser.getDepth()) {
                        case 2:
                            regions.add(smallerRegion(parser));
                            break;
                        case 3:
                            if (regions != null) {
                                Region region = smallerRegion(parser);
                                regions.get(regions.size() - 1).setAddUnderRegions(region);


                                /*region(parser);*/

                            }
                            break;
                        case 4:
                            if (regions.get(regions.size() - 1).getUnderRegions().
                                    get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                    .getUnderRegions() != null) {

                                regions.get(regions.size() - 1).getUnderRegions().
                                        get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                        .setAddUnderRegions(smallerRegion(parser));
                            }
                            break;


                        case 5:

                            if (regions.get(regions.size() - 1).getUnderRegions().
                                    get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                    .getUnderRegions() != null) {

                                regions.get(regions.size() - 1).getUnderRegions().
                                        get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                        .getUnderRegions().get(regions.get(regions.size() - 1).getUnderRegions().
                                        get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                        .getUnderRegions().size() - 1).setAddUnderRegions(smallerRegion(parser));
                            }
                            break;

                        case 6:
                            if (regions.get(regions.size() - 1).getUnderRegions().
                                    get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                    .getUnderRegions().get(regions.get(regions.size() - 1).getUnderRegions().
                                            get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                            .getUnderRegions().size() - 1).getUnderRegions() != null) {

                                regions.get(regions.size() - 1).getUnderRegions().
                                        get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                        .getUnderRegions().get(regions.get(regions.size() - 1).getUnderRegions().
                                        get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                        .getUnderRegions().size() - 1).getUnderRegions().get(regions.get(regions.size() - 1).getUnderRegions().
                                        get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                        .getUnderRegions().get(regions.get(regions.size() - 1).getUnderRegions().
                                                get(regions.get(regions.size() - 1).getUnderRegions().size() - 1)
                                                .getUnderRegions().size() - 1).getUnderRegions().size() - 1).setAddUnderRegions(smallerRegion(parser));

                            }
                            break;
                    }
                }
                parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            System.err.println("any wars");
        }
        /*###############################################################*/


        prefixAndSuffix(regions.get(0));

        print(regions);


    }

    private void print(ArrayList<Region> regions) {
        for (Region region:regions){
            System.out.println(region);
            if (region.getUnderRegions().size()!=0){
                print(region.getUnderRegions());
            }
        }
    }


    private void prefixAndSuffix(Region parentRegions) {
        for (Region region : parentRegions.getUnderRegions()) {
   /*1 Если указано download_suffix,
    download_prefix, то
     download_name = download_prefix + "_" + name + "_" + download_suffix*/
            if (region.getDownloadPrefix()!=null&&region.getDownloadSuffix()!=null){
                region.setDownloadName("http://download.osmand.net/download.php?standard=yes&file="+region.getDownloadPrefix() + "_" + region.getNormalName() + "_" + region.getDownloadSuffix()+"_2.obf.zip");
            }else
            /*Если указан только download_suffix
                download_name = name + "_" + download_suffix

*/
            if (region.getDownloadPrefix()==null&&region.getDownloadSuffix()!=null){
                region.setDownloadName("http://download.osmand.net/download.php?standard=yes&file="+region.getNormalName() + "_" + region.getDownloadSuffix()+"_2.obf.zip");
            }else

                           /*
              Если указан только download_prefix
              download_name = download_prefix + "_" + имя */
            if (region.getDownloadPrefix()!=null&&region.getDownloadSuffix()==null){
                region.setDownloadName("http://download.osmand.net/download.php?standard=yes&file="+region.getDownloadPrefix() + "_" + region.getNormalName()+"_2.obf.zip");

                if (parentRegions.getDownloadSuffix()!=null){
                    region.setDownloadSuffix(parentRegions.getDownloadSuffix());

                }else if (parentRegions.getInnerDownloadSuffix()!=null){
                    region.setDownloadSuffix(parentRegions.getInnerDownloadSuffix());
                }

            }else

            /*
               2. Если download_suffix не указан,
                он берется из родительского региона
                 или из родительского родительского региона ...
*/

            if (region.getDownloadPrefix()==null&&region.getDownloadSuffix()==null)
                if (parentRegions.getDownloadSuffix()!=null){
                    region.setDownloadSuffix(parentRegions.getDownloadSuffix());
                    region.setDownloadName("http://download.osmand.net/download.php?standard=yes&file="+region.getNormalName() + "_" + region.getDownloadSuffix()+"_2.obf.zip");

                }else if (parentRegions.getInnerDownloadSuffix()!=null){
                    region.setDownloadSuffix(parentRegions.getInnerDownloadSuffix());
                    region.setDownloadName("http://download.osmand.net/download.php?standard=yes&file="+region.getNormalName() + "_" + region.getDownloadSuffix()+"_2.obf.zip");
                }

            /*
               2.1 Если в родительском регионе
                 есть inner_download_suffix,
                  то download_suffix = inner_download_suffix*/

                if (region.getUnderRegions().size() != 0) {
                    prefixAndSuffix(region);
                }



        }
    }




    private Region smallerRegion(XmlPullParser parser) {
        Region smallRegion = new Region();
        for (int i = 0; i < parser.getAttributeCount(); i++) {

        //    Log.i("Attribute", i + " : " + parser.getAttributeName(i) + " = " + parser.getAttributeValue(i) + " глубина " + parser.getDepth());
            switch (parser.getAttributeName(i)) {
                case "name":
                    smallRegion.setName(parser.getAttributeValue(i));
                    smallRegion.setDeeply(parser.getDepth());
                    break;

                case "download_suffix":
                    smallRegion.setDownloadSuffix(parser.getAttributeValue(i));
                    break;
                case "inner_download_suffix":
                    smallRegion.setInnerDownloadSuffix(parser.getAttributeValue(i));
                    break;
                case "download_prefix":
                    smallRegion.setDownloadPrefix(parser.getAttributeValue(i));
                    break;
                case "inner_download_prefix":
                    smallRegion.setInnerDownloadPrefix(parser.getAttributeValue(i));
                    break;
                case "map":
                    smallRegion.setMap(parser.getAttributeValue(i));
                    break;
                case "srtm":
                    smallRegion.setSrtm(parser.getAttributeName(i));
                    break;

            }

        }
        return smallRegion;
    }


    public ArrayList<Region> getRegions() {
        return regions;
    }
}
