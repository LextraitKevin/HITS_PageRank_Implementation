import java.lang.reflect.Array;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by kevin on 29/05/2017.
 */
public class algo {

    private static final String FILENAME = "gr0.California2.txt";

    public static List dataFiles = new ArrayList();


    public static Map<Integer,ArrayList> dataset = new HashMap<>();

    private static void readfile(){

        BufferedReader br=null;
        FileReader fr=null;

        try {

            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(FILENAME));

            while ((sCurrentLine = br.readLine()) != null) {

                String[] tab = sCurrentLine.split("\\s+");
                DataFile data = new DataFile(tab[0],Integer.parseInt(tab[1]),tab[2]);

                dataFiles.add(data);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }


    public static void HITS(Map<Integer,ArrayList> graph, Integer k){

        int numNodes = dataset.size();

        Integer maxKey = Collections.max(graph.keySet());


        System.out.println(maxKey);
        // Arrays for hub and authority scores.
        double[] authorityScores = new double[maxKey+1];
        double[] hubScores = new double[maxKey+1];

        // All scores are initially 1.
        Arrays.fill(authorityScores, 1.0);
        Arrays.fill(hubScores, 1.0);

        // Run authority update step and hub update step
        // sequentially for k iterations.
        for (int i=0; i<k; i++) {

            // Keep track of a normalization value.
            double norm = 0.0;

            // Update authority scores.

            for(Map.Entry<Integer, ArrayList> entry : graph.entrySet()){
                Integer key = entry.getKey();
                ArrayList value = entry.getValue();
            //for (int j = 0; j < numNodes; j++) {

                // Authority update step: the authority score for a node
                // is the sum of the hub scores of the nodes that point to it.
                double authScore = 0.0;

                List<Integer> incoming = (List<Integer>) graph.get(key).get(1);


                if(incoming!=null) {
                    for (int incNei = 0; incNei < incoming.size() - 1; incNei++) {
                        authScore += hubScores[incNei];
                    }
                }


                authorityScores[key] = authScore;
                norm += Math.pow(authScore, 2);
            }
            // Normalize authority scores.
            norm = Math.sqrt(norm);


            for (int j=0; j<numNodes; j++) {
                authorityScores[j] = authorityScores[j] / norm;
            }

            // Set normalization value back to zero.
            norm = 0.0;


            // Update hub scores.

            for(Map.Entry<Integer, ArrayList> entry : graph.entrySet()){
                Integer key = entry.getKey();
                ArrayList value = entry.getValue();
            //for (int j=0; j<numNodes; j++) {

                // Hub update step: the hub score for a node is the sum
                // of the authority scores of the nodes it points to.
                double hubScore = 0.0;

                List<Integer> outgoing = (List<Integer>) graph.get(key).get(0);
                if(outgoing!=null) {
                    for (int outNei = 0; outNei < outgoing.size(); outNei++) {
                        hubScore += authorityScores[outNei];
                    }
                }

                hubScores[key] = hubScore;
                norm += Math.pow(hubScore, 2);
            }

            // Normalize hub scores.
            norm = Math.sqrt(norm);
            for(Map.Entry<Integer, ArrayList> entry : graph.entrySet()){
                Integer key = entry.getKey();
                ArrayList value = entry.getValue();
            //for (int j=0; j<numNodes; j++) {
                hubScores[key] = hubScores[key] / norm;

                System.out.println("Key : "+ key + " HUB : "+ hubScores[key] + " ----  AUTHO : " + authorityScores[key]);
            }

            System.out.println("-------------------------------------------------");

        }


    }


    public static void getAdjMatric(Map<Integer,ArrayList> graph){

        Integer maxKey = Collections.max(graph.keySet());


        Integer[][] Adjmatrix = new Integer[maxKey][maxKey-1];

        int it=0;

        for(int i=0 ; i< maxKey ;i++) {


            System.out.println(i);

            try {
                List<Integer> outgoing = (List<Integer>) graph.get(i).get(0);
            }catch (NullPointerException exep){

            }

            for(int j=0;j<maxKey-1;j++){
                //System.out.println(j);


                Adjmatrix[it][j]=0;
                //System.out.println(Adjmatrix[it][i]);
            }

            System.out.println("--------------------------------------------------------");



            it++;
        }


    }




    public static void main(String [] args){

        readfile();



        for(int i=0; i<dataFiles.size();i++){

            DataFile data = (DataFile) dataFiles.get(i);

            //On calcule enfants et parents
            if(data.getType().equals("e")){

                ArrayList oldValueschild = dataset.get(data.getParam().first);
                ArrayList oldValuesparent = dataset.get(Integer.parseInt(data.getParam().second));


                if( oldValueschild==null){

                    //System.out.println("null");
                    oldValueschild = new ArrayList();
                    List<Integer> enfants = new ArrayList<>();
                    List<Integer> parents = new ArrayList<>();

                    oldValueschild.add(enfants);
                    oldValueschild.add(parents);

                }
                if( oldValuesparent==null){

                    //System.out.println("null");
                    oldValuesparent = new ArrayList();
                    List<Integer> enfants = new ArrayList<>();
                    List<Integer> parents = new ArrayList<>();
                    oldValuesparent.add(enfants);
                    oldValuesparent.add(parents);

                }

                List<Integer> enfants1 = (List<Integer>) oldValueschild.get(0);
                List<Integer> parents1 = (List<Integer>) oldValueschild.get(1);


                List<Integer> enfants2 = (List<Integer>) oldValuesparent.get(0);
                List<Integer> parents2 = (List<Integer>) oldValuesparent.get(1);

                enfants1.add(Integer.parseInt(data.getParam().second));
                parents2.add(data.getParam().first);

                ArrayList newValues1 = new ArrayList<>();
                ArrayList newValues2 = new ArrayList<>();

                newValues1.add(enfants1);
                newValues1.add(parents1);

                newValues2.add(enfants2);
                newValues2.add(parents2);

                dataset.put(data.getParam().first,newValues1);
                dataset.put(Integer.valueOf(data.getParam().second),newValues2);

            }
        }

        //HITS(dataset,10);

        getAdjMatric(dataset);

        /*
        for(int i =0 ;i <dataset.size();i++){

            System.out.println("Key : " + i + " tab : "+ dataset.get(i));
        }*/


    }
}



