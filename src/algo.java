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

    //Sample file reading
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

    public static void calc(Integer maxKeys, Integer[][] AdjMatrix){

        double InitialPageRank;
        double OutgoingLinks=0;
        double DampingFactor = 0.85;
        double TempPageRank[] = new double[maxKeys+1];
        double pagerank[] = new double[maxKeys+1];
        maxKeys = maxKeys + 1 ;

        int col;
        int row;
        int k=1;
        int step=1;

        InitialPageRank = 1/Double.parseDouble(maxKeys.toString());
        System.out.printf("Initial PageRank  :"+InitialPageRank+"\n");


        for(k=0;k<maxKeys;k++)
        {
            pagerank[k]=InitialPageRank;
        }
        System.out.printf("\n Initial PageRank Values , 0th Step \n");
        for(k=0;k<maxKeys;k++)
        {
            System.out.printf(" Page Rank of "+k+" is :\t"+pagerank[k]+"\n");
        }
        while(step<=2) // Iterations
        {
            for(k=0;k<maxKeys;k++)
            {
                TempPageRank[k]=pagerank[k];
                pagerank[k]=0;
            }
            for(row=0;row<maxKeys;row++)
            {
                for(col=0;col<maxKeys;col++)
                {
                    if(AdjMatrix[col][row] == 1)
                    {
                        k=0;
                        OutgoingLinks=0;
                        while(k<maxKeys)
                        {
                            if(AdjMatrix[col][k] == 1 )
                            {
                                OutgoingLinks=OutgoingLinks+1;
                            }
                            k=k+1;
                        }
                        // Calculate PageRank
                        pagerank[row]+=TempPageRank[col]*(1/OutgoingLinks);
                    }
                }
            }

            System.out.printf("\n After "+step+"th Step \n");
            for(k=0;k<maxKeys;k++)
                System.out.printf(" Page Rank of "+k+" is :\t"+pagerank[k]+"\n");
            step = step+1;
        }

        //Damping Factor
        for(k=0;k<maxKeys;k++)
        {
            pagerank[k]=(1-DampingFactor)+ DampingFactor*pagerank[k];
        }

        System.out.printf("\n Final Page Rank : \n");
        for(k=0;k<maxKeys;k++)
        {
            System.out.printf(" Page Rank of "+k+" is :\t"+pagerank[k]+"\n");
        }

    }


    public static void HITS(Map<Integer,ArrayList> graph, Integer k){
        //En commentaires : pseudo code de Wikipédia
        int numNodes = dataset.size();
        Integer maxKey = Collections.max(graph.keySet());

        double[] authorityScores = new double[maxKey+1];
        double[] hubScores = new double[maxKey+1];

        Arrays.fill(authorityScores, 1.0); // p.auth = 1 // p.auth is the authority score of the page p
        Arrays.fill(hubScores, 1.0);    // p.hub = 1 // p.hub is the hub score of the page p


        for (int i=0; i<k; i++) {   //for step from 1 to k do // run the algorithm for k steps

            double norm = 0.0;

            for(Map.Entry<Integer, ArrayList> entry : graph.entrySet()){    //for each page p in G do  // update all authority values first
                Integer key = entry.getKey();
                ArrayList value = entry.getValue();

                double authScore = 0.0;
                List<Integer> incoming = (List<Integer>) graph.get(key).get(1);

                if(incoming!=null) {
                    for (int incNei = 0; incNei < incoming.size() - 1; incNei++) {  //for each page q in p.incomingNeighbors do // p.incomingNeighbors is the set of pages that link to p
                        authScore += hubScores[incNei]; //p.auth += q.hub
                    }
                }

                authorityScores[key] = authScore;
                norm += Math.pow(authScore, 2); //norm += square(p.auth) // calculate the sum of the squared auth values to normalise
            }

            norm = Math.sqrt(norm);

            for (int j=0; j<numNodes; j++) {    //for each page p in G do  // update the auth scores
                authorityScores[j] = authorityScores[j] / norm; // p.auth = p.auth / norm  // normalise the auth values
            }

            norm = 0.0;

            for(Map.Entry<Integer, ArrayList> entry : graph.entrySet()){    //for each page p in G do  // then update all hub values
                Integer key = entry.getKey();
                ArrayList value = entry.getValue();

                double hubScore = 0.0;  //p.hub = 0

                List<Integer> outgoing = (List<Integer>) graph.get(key).get(0);
                if(outgoing!=null) {
                    for (int outNei = 0; outNei < outgoing.size(); outNei++) {   //for each page r in p.outgoingNeighbors do // p.outgoingNeighbors is the set of pages
                        hubScore += authorityScores[outNei];
                    }
                }

                hubScores[key] = hubScore;
                norm += Math.pow(hubScore, 2);  //norm = sqrt(norm)
            }

            norm = Math.sqrt(norm);
            for(Map.Entry<Integer, ArrayList> entry : graph.entrySet()){    // for each page p in G do  // then update all hub values
                Integer key = entry.getKey();
                hubScores[key] = hubScores[key] / norm; //p.hub = p.hub / norm   // normalise the hub values

                System.out.println("Key : "+ key + " HUB : "+ hubScores[key] + " ----  AUTHO : " + authorityScores[key]);
            }
            System.out.println("-------------------------------------------------");
        }
    }


    public static Integer[][] getAdjMatric(Map<Integer,ArrayList> graph){

        Integer maxKey = Collections.max(graph.keySet());

        Integer[][] Adjmatrix = new Integer[maxKey+1][maxKey+1];

        List<Integer> outgoing = new ArrayList<>();

        int it=0;

        for(int i=0 ; i<= maxKey ;i++) {
            outgoing.clear();
            try {
                outgoing = (List<Integer>) graph.get(i).get(0);
            }catch (NullPointerException exep){

            }
            for(int j=0;j<=maxKey;j++){
                Adjmatrix[it][j]=0;
            }
            for(int k=0;k<outgoing.size();k++) {
                Adjmatrix[it][outgoing.get(k)]=1;
            }
            it++;
        }

        return Adjmatrix;

    }


    public static void showAdjMatrix(Integer[][] Adjmatrix , int maxKey){

        for(int i=0 ; i<= maxKey ;i++) {

            for(int j=0;j<=maxKey;j++) {
                System.out.println(Adjmatrix[i][j]);
            }
            System.out.println("-------");

        }
    }




    public static void main(String [] args){

        readfile();

        Integer[][] AdjMatrix;



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

        System.out.println("\n \n ****************************************   Algorithme HITS  ******************************************* \n");
        HITS(dataset,10);
        AdjMatrix = getAdjMatric(dataset);
        Integer maxKey = Collections.max(dataset.keySet());
        //showAdjMatrix(AdjMatrix, maxKey);

        System.out.println("\n \n ****************************************  Algorithme PageRank ******************************************* \n");
        calc(maxKey,AdjMatrix);




    }
}
