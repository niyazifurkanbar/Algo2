/*
 * Bu örnekte bir txt dosyasında bulunan öğrenci ve not bilgilerinden ders başarı durumunu hesaplama işlemi yapılmıştır.
 * Bu örnekten edinilecek kazanımlar;
 * 1. Try-Catch (Exceptions) Yapısı
 * 2. Dosya işlemleri (Dosya açma, Dosya varlığını kontrol etme, Dosya oluşturma, Dosyayı okuma, Dosya silme vb.)
 * 3. Sınıf-Nesne (Sınıf oluşturma, Nesne türetme, Erişim belirleyiciler, Kurucu metot, Static ve this anahtar kelimesi vb.)
 * 4. 'public static void main' metotu kullanılmasının sebebi
 */

package com.mycompany.algo2_ornek1;


/* 
 * Kod içerisinde kullanılacak harici sınıfların dahil edilmesi
 * Scanner sınıfı, kurucu metotunda verilen parametreye göre okuma işlemi yapar (ör: klavye, dosya, vb.)
 * File sınıfı, kod içerisinde dosya/klasör işlemleri yapmaya olanak sağlar (ör: dosya/klasör açma, dosya/klasör oluşturma, vb.)
 * PrintWriter sınıfı, kurucu metotunda verilen parametreye göre yazma işlemi yapar
*/

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

/*
 * Öğrencilerin bilgilerinin tutulacağı sınıfın oluşturulması
 * Sınıfın erişim belirleyicisi olarak default olan değer kullanılmıştır. (sadece aynı paketten sınıfların ulaşması için)
 * Kurucu metodunun erişim belirleyicisi olarak public kullanılmıştır, bu sayede diğer kurucu metot çağırılabilir hale gelmiştir.
*/

class Ogrenci{
    
    /*
     * _id değişkeninin static oluşturulmasıyla, daha önce kaç tane öğrenci eklediğimizi tutma gerekliliğimiz...
     * ... ortadan kalkmıştır ve id değişkenine kolaylıkla id ataması yapmamızı sağlamıştır.
     * Öğrencinin ve sınıfın değiştirilmemesi gereken bilgilere private erişim belirleyici kullanılmıştır.
     * Değiştirilebilir ara sınav, genel sınav notu değişkenleri de private yapılarak, not değiştirildiğinde...
     * ...ortalamanın yeniden hesaplanması sağlanmıştır. Diğer değikenler sadece okunabilir olması için get metotları yazılmıştır...
     * Bu durum ilerleyen zamanlarda göreceğiniz kapsülleme işlemidir. 
     * Bu sayede direkt dışardan müdahale edilemeyecek, sadece nesne oluşturulurken belirlenebilecektir.
    */
    private static int _id = 1;
    private int id, ara, genel;
    private double ortalama;
    private String okulNo, ad, soyad;

    public Ogrenci(String okulNo, String ad, String soyad, int ara, int genel) {
        /*
         * Farkettiğiniz üzere; kurucu metota parametre olarak gelen değişkenler ile sınıfın değişkenleri aynı isme sahip,
         * Bu durumda hangi değişken kullanılacaktır? Java'da en yakın değişken kullanılır yani özelden genele...
         * yerel değişkenler öncelikle olmak üzere kapsam bazlı kullanılır.
         * yerel değişken > global değişken
         * Bu durumu aşmak için ya aynı isimli değişken kullanılmaması gerekir ya da 'this' anahtar kelimesi...
         * this anahtar kelimesi sınıfı belirtir. 'this.id' yazıldığı zaman derleyici bunu sınıfın id isimli değişkeni diye anlar.
        */
        this.id = _id;
        this.ara = ara;
        this.genel = genel;
        this.ortalama = this.ortalamaAl(this.ara,this.genel);
        this.okulNo = okulNo;
        this.ad = ad;
        this.soyad = soyad;
        
        /*
         * Her yeni öğrenci oluşturulduğunda sınıfın genel idsini 1 arttırıyoruz
         * _id = id+1; veya
         * _id += 1;
         * _id++;
         * Burada this kullanılmamasının sebebi, _id adında bir değişken yerelde bulunmamaktadır.
         * Dolayısıyla ulaşılan _id değişkeni direkt sınıfın değişkeni olan _id 'dir.
        */ 
        _id = _id+1;
    }
    
    private double ortalamaAl(int ara, int genel){
        return ara*.4+genel*.6;
    }

    // Kapsülleme get metotları, private değişkenleri dışarıdan okunabilir yapmak için
    public int getId() {
        return id;
    }

    public String getOkulNo() {
        return okulNo;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public int getAra() {
        return ara;
    }

    public int getGenel() {
        return genel;
    }

    public double getOrtalama() {
        return ortalama;
    }
    
    // Kapsülleme set metotları, private olup da değiştirildiği zaman yeniden işlem yapılması gerektiği zaman
    public void setAra(int ara) {
        this.ara = ara;
        this.ortalama = ortalamaAl(this.ara, this.genel);
    }
    
    public void setGenel(int genel) {
        this.genel = genel;
        this.ortalama = ortalamaAl(this.ara, this.genel);
    }
    

}

public class ALGO2_ORNEK1 {

    public static void main(String[] args) {
        /* 
         * Try-catch yapısı tüm kodun başlangıcına koyulduğu ve catch kısmına tüm özel istisnaların üst sınıfı olan...
         * Exeception sınıfından oluşturulan nesne verilirse koddaki tüm hatalar yakalanmış olur.
         * Bunun yerine direkt metota throws anahtar kelimesiyle de yapılabilir veya sadece hata oluşabilecek kod parçacığı...
         * ... try-catch yapısı içerisine alınabilir.
        */
        try {
            /*
            * Bir sınıftan nesne türetmek için çoğu C tabanlı programlama dilinde kullanılan yapı aynıdır.
            * Bu yapı şu şekildedir: SinifAdi nesneAdi = new SinifAdi();
            * Bu yapıdaki nesneAdi tamamen bir değişken adı gibidir, değişken isimlendirme kurallarına bağlı kalarak...
            * ... istediğiniz adı verebilirsiniz.
            */
            
           // File sınıfından nesne türetirken kurucu metot içerisine dosyanın yolu yazılır, './' ifadesi mevcut klasörü belirtir.
           File notlar = new File("./notlar.txt");
           
           // Scanner sınıfından nesne türetilirken kurucu metot içerisine okunacak kaynak verilir, bu örnekte bu bir File nesnesidir.
           Scanner oku = new Scanner(notlar);
           
           /*
            * Döngüden önce değişken oluşturularak, sürekli değişken oluşturulmasının önüne geçer, RAM'deki aynı yeri kullanır.
            * Okunacak dosyanın içerisindeki bilgi miktarını bilmediğimizden while döngüsü ile okunacak veri kalmayıncaya dek okuma işlemi yapılır.
            * hasNextLine metodu, okunacak sonraki bir satır var mı diye kontrol eder.
            * nextLine metodu, satır satır okuma işlemi yapar.
            */
           String hamVeri = "";
           String[] hamVeriDizi = new String[5];
           Ogrenci[] ogrenciler = new Ogrenci[50];
           int i = 0;
           while(oku.hasNextLine()){
               hamVeri = oku.nextLine();
               hamVeriDizi = hamVeri.split(",",5);
               ogrenciler[i] = new Ogrenci(hamVeriDizi[0], hamVeriDizi[1], hamVeriDizi[2], Integer.parseInt(hamVeriDizi[3]), Integer.parseInt(hamVeriDizi[4]));
               i++;
           }
           
           // Dersten başarı olanları ve başarısız olanları iki ayrı dosyaya yazmak için iki dosya açıyoruz.
           File basarili = new File("./basarili.txt");
           File basarisiz = new File("./basarisiz.txt");
           
           //Açmak istediğimiz dosyalar yoksa yeni dosya oluşturuyoruz.
           if(!basarili.exists()) basarili.createNewFile();
           if(!basarisiz.exists()) basarisiz.createNewFile();
           
           // Dosyaya yazmak için PrintWriter nesnelerini oluşturuyoruz.
           PrintWriter basariliYazici = new PrintWriter(basarili);
           PrintWriter basarisizYazici = new PrintWriter(basarisiz);
           
           String icerik = "";
            for (Ogrenci ogrenci : ogrenciler) {
                // Dizide maksimum sayıdaki öğrenci yoksa dizideki diğer elemanlar boş olacağından döngüden çıkıyoruz.
                if(ogrenci == null) break;
                icerik = ogrenci.getId()+","+ogrenci.getOkulNo()+","+ogrenci.getAd()+","+ogrenci.getSoyad();
                if(ogrenci.getOrtalama() >= 50){
                    basariliYazici.print(icerik+"\n");
                    System.out.println(icerik+", BAŞARILI");
                }else{
                    basarisizYazici.print(icerik+"\n");
                    System.out.println(icerik+", BAŞARISIZ");
                }
            }
            
            basariliYazici.close();
            basarisizYazici.close();
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Bir hata meydana geldi!");
        }
        
    }
}
