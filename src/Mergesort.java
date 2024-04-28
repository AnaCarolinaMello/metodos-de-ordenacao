import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mergesort {
    private static int swaps = 0, comparisons = 0;
    private static long executionTime = 0;

    public static void main(String[] args) throws Exception {
        List<Acomadacao> acomadacoes = getData();
        List<Acomadacao> newAcomadacoes = ler(acomadacoes);
        long startTime = System.currentTimeMillis();
        sort(newAcomadacoes, 0, newAcomadacoes.size() - 1);
        executionTime = System.currentTimeMillis() - startTime;
        imprimirAll(newAcomadacoes);
        createLog();
    }

    public static void sort(List<Acomadacao> acomodacoes, int left, int right) throws Exception {
        if (left < right) {
            int middle = (left + right) / 2;
            sort(acomodacoes, left, middle);
            sort(acomodacoes, middle + 1, right);
            intercalate(acomodacoes, left, middle, right);
        }
    }

    public static void intercalate(List<Acomadacao> acomodacoes, int left, int middle, int right) throws Exception {
        int size1, size2, k, i = 0, j = 0;

        size1 = middle - left + 1;
        size2 = right - middle;

        List<Acomadacao> list1 = new ArrayList<Acomadacao>();
        List<Acomadacao> list2 = new ArrayList<Acomadacao>();

        for (int l = 0; l < size1; l++) {
            list1.add(acomodacoes.get(l + left));
        }

        for (int l = 0; l < size2; l++) {
            list2.add(acomodacoes.get(middle + l + 1));
        }

        for (k = left; i < size1 && j < size2; k++) {
            Acomadacao acomadacao1 = list1.get(i);
            Acomadacao acomadacao2 = list2.get(j);
            boolean isBigger = compare(acomodacoes, acomadacao1, acomadacao2);
            if (!isBigger) swap(acomodacoes, k, list1.get(i++));
            else swap(acomodacoes, k, list2.get(j++));
        }

        if (size1 == i) {
            for (; k <= right; k++) {
                swap(acomodacoes, k, list2.get(j++));
            }
        } else {
            for (; k <= right; k++) {
                swap(acomodacoes, k, list1.get(i++));
            }
        }
    }

    public static void swap(List<Acomadacao> acomodacoes, int positionToChange, Acomadacao acomadacaoToInsert) {
        swaps++;
        acomodacoes.set(positionToChange, acomadacaoToInsert);
    }

    public static boolean compare(List<Acomadacao> acomodacoes, Acomadacao smallAcomadacao, Acomadacao newAcomadacao) {
        comparisons++;
        boolean isBigger = false;

        if (smallAcomadacao.getHostId() == newAcomadacao.getHostId()) {
            if (smallAcomadacao.getRoomId() > newAcomadacao.getRoomId()) isBigger = true;
        } else if (smallAcomadacao.getHostId() > newAcomadacao.getHostId()) {
            isBigger = true;
        }

        return isBigger;
    }

    public static void createLog() {
        String fileToSave = "00801198_mergesort.txt";
        String log = "00801198\t" + executionTime + "ms\t" + comparisons + "\t" + swaps;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
            writer.write(log);
        } catch (Exception e) {
        }
    }

    public static void imprimirAll(List<Acomadacao> acomodacoes) {
        for (Acomadacao acomadacao : acomodacoes) {
            acomadacao.imprimir();
        }
    }

    public static List<Acomadacao> ler(List<Acomadacao> acomodacoes) {
        List<Acomadacao> newAcomadacoes = new ArrayList<Acomadacao>();
        Scanner scanner = new Scanner(System.in);
        int endWhen = Integer.parseInt(scanner.nextLine());
        int isAt = 0;
        String s = scanner.nextLine();

        while (endWhen > isAt) {
            isAt++;
            String copy = s;
            try {
                Acomadacao acomodacao = 
                acomodacoes.stream()
                    .filter((value) -> value.getRoomId() == Integer.parseInt(copy))
                    .findFirst()
                    .get();
                newAcomadacoes.add(acomodacao);
            } catch (Exception e) {
            }
            try {
                s = scanner.nextLine();
            } catch (Exception e) {
            }
        }
        scanner.close();
        return newAcomadacoes;
    }

    public static List<Acomadacao> getData() {
        List<Acomadacao> acomadacoes = new ArrayList<Acomadacao>();
        String fileName = "/tmp/dados_airbnb.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            line = br.readLine();

            while (line != null) {
                String[] attributes = line.split("\t");
                Acomadacao acomadacao = 
                    new Acomadacao(
                        Integer.parseInt(attributes[0]),
                        Integer.parseInt(attributes[1]),
                        attributes[2] ,
                        attributes[3],
                        attributes[4],
                        attributes[5],
                        Integer.parseInt(attributes[6]),
                        Double.parseDouble(attributes[7]),
                        Integer.parseInt(attributes[8]),
                        Double.parseDouble(attributes[9]),
                        Double.parseDouble(attributes[10]),
                        attributes[11]
                    );
                acomadacoes.add(acomadacao);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
        }

        return acomadacoes;
    }
}
class Acomadacao {
    private int roomId;
    private int hostId;
    private String roomType;
    private String country;
    private String city;
    private String neighbourhood;
    private int reviews;
    private double overallSatisfaction;
    private int accommodates;
    private double bedrooms;
    private double price;
    private String propertyType;

    public Acomadacao() {
        setRoomId(0);
        setHostId(0);
        setRoomType("");
        setCountry("");
        setCity("");
        setNeighbourhood("");
        setReviews(0);
        setOverallSatisfaction(0);
        setAccommodates(0);
        setBedrooms(0);
        setPrice(0);
        setPropertyType("");
    }

    public Acomadacao(int roomId, int hostId, String roomType, String country, String city, String neighbourhood,
            int reviews, double overallSatisfaction, int accommodates, double bedrooms, double price,
            String propertyType) {
        setRoomId(roomId);
        setHostId(hostId);
        setRoomType(roomType);
        setCountry(country);
        setCity(city);
        setNeighbourhood(neighbourhood);
        setReviews(reviews);
        setOverallSatisfaction(overallSatisfaction);
        setAccommodates(accommodates);
        setBedrooms(bedrooms);
        setPrice(price);
        setPropertyType(propertyType);
    }

    public static void ler(List<Acomadacao> acomodacoes) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        while (!s.equals("FIM")) {
            String copy = s;
            Acomadacao acomodacao = 
                acomodacoes.stream()
                    .filter((value) -> value.getRoomId() == Integer.parseInt(copy))
                    .findFirst()
                    .get();
            acomodacao.imprimir();

            s = scanner.nextLine();
        }
        scanner.close();
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHostId() {
        return this.hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighbourhood() {
        return this.neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public int getReviews() {
        return this.reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public double getOverallSatisfaction() {
        return this.overallSatisfaction;
    }

    public void setOverallSatisfaction(double overallSatisfaction) {
        this.overallSatisfaction = overallSatisfaction;
    }

    public int getAccommodates() {
        return this.accommodates;
    }

    public void setAccommodates(int accommodates) {
        this.accommodates = accommodates;
    }

    public double getBedrooms() {
        return this.bedrooms;
    }

    public void setBedrooms(double bedrooms) {
        this.bedrooms = bedrooms;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void imprimir() {
        System.out.println(toString());
    }

    public Acomadacao clone() {
        return new Acomadacao(roomId, hostId, roomType, country, city, neighbourhood, reviews, overallSatisfaction, accommodates, bedrooms, price, propertyType);
    }

    @Override
    public String toString() {
        return "[" +
            getRoomId() + " ## " +
            getHostId() + " ## " +
            getRoomType() + " ## " +
            getCountry() + " ## " +
            getCity() + " ## " +
            getNeighbourhood() + " ## " +
            getReviews() + " ## " +
            getOverallSatisfaction() + " ## " +
            getAccommodates() + " ## " +
            getBedrooms() + " ## " +
            getPrice() + " ## " +
            getPropertyType() +
        "]";
    }
}
