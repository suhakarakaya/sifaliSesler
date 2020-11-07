package com.suhakarakaya.sifalisesler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var dataList = ArrayList<Data>()
    }

    //var dataList: ArrayList<Data>? = null
    lateinit var mainPageAdapter: MainPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        mainPageAdapter = MainPageAdapter(applicationContext, dataList)
        gv_images.adapter = mainPageAdapter
    }

    private fun initData() {
        dataList.add(
            Data(
                1,
                "Buselik Makamı",
                "Kalça kemiği ağrısı ve baş ağrısının tedavisinde yardımcı olarak uygulanmıştır. Kuvvet ve barış duygusu verir.",
                "Effective in the treatment of pains in the hips and head. It provokes the feelings of strength and peace.",
                R.raw.buselik,
                R.drawable.buselik,
                "Eser/Music: Kemençeci Nikolaki"
            )
        )
        dataList.add(
            Data(
                2,
                "Rast Makamı",
                "Felç hastalarının tedavisinde yardımcı olarak uygulanmıştır. Neşe, iç huzuru ve rahatlık verir..",
                "Effective in the treatment of paralytic patients. It brings a person happiness, inner peace and a sense of comfort.",
                R.raw.rast,
                R.drawable.rast,
                "Eser/Music: Benli Hasan Ağa"
            )
        )
        dataList.add(
            Data(
                3,
                "İsfahan Makamı",
                "Hatırlatma ve düşünme gücünü arttırıcı özelliği olduğu düşünülür.",
                "It is believed to promote memory and cognitive skills.",
                R.raw.isfahan,
                R.drawable.isfahan,
                "Eser/Music: Tanburi İzak Efendi"
            )
        )
        dataList.add(
            Data(
                4,
                "Rehavi Makamı",
                "Baş ağrısı, çarpıntı, felç, balgam ve kanla ilgili hastalıkların tedavisinde yardımcı olarak uygulanmıştır. Sonsuzluk ve yer çekiminden kurtulma duygusu verir..",
                "Effective in the treatment of headache, palpitation, paralysis and diseases related to blood and phlegm.",
                R.raw.rehavi,
                R.drawable.rehavi,
                "Eser/Music: Tanburi İzak Efendi"
            )
        )
        dataList.add(
            Data(
                5,
                "Hicaz Makamı",
                "Uyku ve idrar sorunlarının, ağrı ve sancıların tedavisinde yardımcı olarak uygulanmıştır. Alçak gönüllülük duygusu verir.",
                "Effective in the treatment of sleep disorders and diseases of urinary tract. It provokes humility.",
                R.raw.hicaz,
                R.drawable.hicaz,
                "Eser/Music: Neyzen Veli Dede"
            )
        )
        dataList.add(
            Data(
                6,
                "Hüseyni Makamı",
                "Kalp, ciğer iltihapları, mide ağrıları, sıtma ve hummanın tedavisinde yardımcı olarak uygulanmıştır. Kendine güven ve kararlılık duygusu verir.",
                "Effective in the treatment of liver and heart diseases, stomachache, malaria and fever. It promotes self-esteem and gives a sense of strong determination.",
                R.raw.huseyni,
                R.drawable.huseyni,
                "Eser/Music: Lavtacı Andon"
            )
        )
        dataList.add(
            Data(
                7,
                "Uşşak Makamı",
                "Romatizmal hastalıklar, uykusuzluk ve ayak ağrılarının tedavisinde yardımcı olarak uygulanmıştır. Gülme, sevinç, kuvvet ve kahramanlık duyguları verir.",
                "Effective in the treatment of rheumatic diseases, insomnia and foot pain. It promotes the feelings of bliss, strength and bravery.",
                R.raw.ussak,
                R.drawable.ussak,
                "Eser/Music: Neyzen Salih Dede"
            )
        )
        dataList.add(
            Data(
                8,
                "Büzürg Makamı",
                "Kulunç ve bağırsak ağrılarını dindirmede etkilidir; zihni berraklaştırmaya yardımcı olur.",
                "Effective in the treatment of cramps, intestinal track, and help clarification of the mind.",
                R.raw.buzurg,
                R.drawable.buzurg,
                "Eser/Music: III. Selim"
            )
        )
        dataList.add(
            Data(
                9,
                "Neva Makamı",
                "Siyatik ve zihinsel hastalıkların tedavisinde yardımcı olarak uygulanmıştır. Kuvvet ve kahramanlık duyguları meydana getirir.",
                "Effective in the treatment of sciatica, and mental diseases. It brings a person the feelings of strength and bravery.",
                R.raw.neva,
                R.drawable.neva,
                "Eser/Music: Sultan II. Bayezid"
            )
        )
        dataList.add(
            Data(
                10,
                "Zengüle Makamı",
                "Kalp, ciğer ve mide hastalıklarının tedavisinde yardımcı olarak uygulanmıştır. Hayal ve sırlar telkin eder, uyku ve masal duygusu verir.",
                "Effective in the treatment of heart, liver and stomach. It provokes imagination and intuition; gives a person sensation of sleep and dream.",
                R.raw.zengule,
                R.drawable.zergule,
                "Eser/Music: Çinuçen Tanrıkorur"
            )
        )
        dataList.add(
            Data(
                11,
                "Zirefkend Makamı",
                "Kısmi felç, sırt ağrıları ve eklem ağrılarının tedavisinde yardımcı olarak uygulanmıştır. Derin duygu hissi verir.",
                "Effective in the treatment of paresis (partial paralysis), backpain and arthralgia. It fosters a sense of strength and depth.",
                R.raw.zirefkend,
                R.drawable.ziref,
                "Eser/Music: Bestekârı Meçhul"
            )
        )
        dataList.add(
            Data(
                12,
                "Irak Makamı",
                "Ateşli hastalıklar, saldırganlık ve nevrotik hastaların tedavisinde yardımcı olarak uygulanmıştır. Lezzet verir, düşünme ve kavrama konusunda etkilidir.",
                "Effective in the treatment of fever (meningitis), psychological ailments such as aggression and neurosis. It provokes the feelings of pleasure, relish; strengthens cognitive skills.",
                R.raw.irak,
                R.drawable.irak,
                "Eser/Music: Kemençeci Nikolaki"
            )
        )
    }
}