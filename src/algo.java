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



    public void HITS(Map graph){

        for(int i=0; i < graph.size(); i++){

            Object variable = graph.get(i);


        }


    }


    public static void main(String [] args){

        readfile();



        for(int i=0; i<dataFiles.size();i++){

            DataFile data = (DataFile) dataFiles.get(i);

            //On calcule enfants et parents
            if(data.getType().equals("e")){

                ArrayList oldValueschild = dataset.get(data.getParam().first);
                ArrayList oldValuesparent = dataset.get(data.getParam().second);


                if( oldValueschild==null){

                    System.out.println("null");
                    oldValueschild = new ArrayList();
                    List<Integer> enfants = new ArrayList<>();
                    List<Integer> parents = new ArrayList<>();

                    oldValueschild.add(enfants);
                    oldValueschild.add(parents);

                }
                if( oldValuesparent==null){

                    System.out.println("null");
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

        System.out.println(dataset.get(0));



    }
}



