package dev.mizoguche.bookmarkcompose.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.mizoguche.bookmarkcompose.domain.Article
import dev.mizoguche.bookmarkcompose.domain.Category
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RunWith(AndroidJUnit4::class)
class HatebuRssParserTest {
    @Test
    fun parseCategory() {
        val rssString = """
            <?xml version="1.0" encoding="UTF-8"?>

            <rdf:RDF
             xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
             xmlns="http://purl.org/rss/1.0/"
             xmlns:admin="http://webns.net/mvcb/"
             xmlns:content="http://purl.org/rss/1.0/modules/content/"
             xmlns:dc="http://purl.org/dc/elements/1.1/"
             xmlns:hatena="http://www.hatena.ne.jp/info/xmlns#"
             xmlns:syn="http://purl.org/rss/1.0/modules/syndication/"
             xmlns:taxo="http://purl.org/rss/1.0/modules/taxonomy/"
            >

            <channel rdf:about="https://b.hatena.ne.jp/hotentry/all">
            <title>&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF; - &#x4EBA;&#x6C17;&#x30A8;&#x30F3;&#x30C8;&#x30EA;&#x30FC; - &#x7DCF;&#x5408;</title>
            <link>https://b.hatena.ne.jp/hotentry/all</link>
            <description>&#x6700;&#x8FD1;&#x306E;&#x4EBA;&#x6C17;&#x30A8;&#x30F3;&#x30C8;&#x30EA;&#x30FC;</description>
            <items>
             <rdf:Seq>
              <rdf:li rdf:resource="https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f" />
              <rdf:li rdf:resource="https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html" />
             </rdf:Seq>
            </items>
            </channel>
            <item rdf:about="https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f">
            <title>&#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x201C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x201D;&#x8A34;&#x3048;&#x308B;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x300D;&#x300C;&#x3061;&#x3083;&#x3093;&#x3068;&#x4ED5;&#x4E8B;&#x3057;&#x3066;&#x308B;&#x4EBA;&#x306B;&#x306F;&#x90AA;&#x9B54;&#x300D;&#xFF08;&#x30B9;&#x30DD;&#x30CB;&#x30C1;&#x30A2;&#x30CD;&#x30C3;&#x30AF;&#x30B9;&#xFF09; - Yahoo!&#x30CB;&#x30E5;&#x30FC;&#x30B9;</title>
            <link>https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f</link>
            <description>&#x300C;2&#x3061;&#x3083;&#x3093;&#x306D;&#x308B;&#x300D;&#x5275;&#x8A2D;&#x8005;&#x306E;&#x897F;&#x6751;&#x535A;&#x4E4B;&#xFF08;&#x3072;&#x308D;&#x3086;&#x304D;&#xFF09;&#x6C0F;&#xFF08;44&#xFF09;&#x304C;11&#x65E5;&#x3001;&#x30A4;&#x30F3;&#x30BF;&#x30FC;&#x30CD;&#x30C3;&#x30C8;&#x653E;&#x9001;&#x306E;ABEMA&#x300C;ABEMA&#x3000;Prime&#x300D;&#xFF08;&#x6708;&#xFF5E;&#x91D1;&#x66DC;&#x5F8C;9&#x30FB;00&#xFF09;&#x306B;&#x30EA;&#x30E2;&#x30FC;&#x30C8;&#x51FA;&#x6F14;&#x3057;&#x300C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x300D;&#x3092;&#x5531;&#x3048;&#x308B;&#x5834;&#x9762;&#x304C;&#x3042;&#x3063;&#x305F;&#x3002; &#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x306F;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x3001;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x3093;&#x3067;&#x3059;&#x3088;&#x306D;&#x3002;&#x6587;&#x7AE0;&#x306B;&#x307E;&#x3068;&#x3081;&#x308B;&#x306E;&#x3063;&#x3066;&#x3001;&#x982D;&#x306E;&#x4E2D;&#x3067;&#x307E;&#x3068;&#x3081;&#x308B;&#x80FD;&#x529B;&#x3092;&#x4F7F;&#x3063;&#x3066;&#x3001;&#x8AAC;&#x660E;&#x6587;&#x3092;&#x66F8;...</description>
            <dc:date>2021-06-11T14:13:32Z</dc:date>
            <dc:subject>&#x4E16;&#x306E;&#x4E2D;</dc:subject>
            <dc:subject>&#x30B3;&#x30DF;&#x30E5;&#x30CB;&#x30B1;&#x30FC;&#x30B7;&#x30E7;&#x30F3;</dc:subject>
            <dc:subject>&#x4ED5;&#x4E8B;</dc:subject>
            <dc:subject>&#x793E;&#x4F1A;</dc:subject>
            <dc:subject>&#x96FB;&#x8A71;</dc:subject>
            <dc:subject>&#x3042;&#x3068;&#x3067;&#x8AAD;&#x3080;</dc:subject>
            <dc:subject>work</dc:subject>
            <dc:subject>&#x3072;&#x308D;&#x3086;&#x304D;</dc:subject>
            <dc:subject>&#x30CD;&#x30BF;</dc:subject>
            <dc:subject>&#x30CB;&#x30E5;&#x30FC;&#x30B9;</dc:subject>
            <taxo:topics>
              <rdf:Bag>
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%82%B3%E3%83%9F%E3%83%A5%E3%83%8B%E3%82%B1%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E4%BB%95%E4%BA%8B" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E7%A4%BE%E4%BC%9A" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E9%9B%BB%E8%A9%B1" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%81%82%E3%81%A8%E3%81%A7%E8%AA%AD%E3%82%80" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=work" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%81%B2%E3%82%8D%E3%82%86%E3%81%8D" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%83%8D%E3%82%BF" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%83%8B%E3%83%A5%E3%83%BC%E3%82%B9" />
              </rdf:Bag>
            </taxo:topics>
            <hatena:bookmarkcount>479</hatena:bookmarkcount>
            <hatena:bookmarkSiteEntriesListUrl>https://b.hatena.ne.jp/site/news.yahoo.co.jp/</hatena:bookmarkSiteEntriesListUrl>
            <hatena:imageurl>https://amd-pctr.c.yimg.jp/r/iwiz-amd/20210611-00000299-spnannex-000-3-view.jpg</hatena:imageurl>
            <hatena:bookmarkCommentListPageUrl>https://b.hatena.ne.jp/entry/s/news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f</hatena:bookmarkCommentListPageUrl>
            <content:encoded>&#x3C;blockquote cite=&#x22;https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f&#x22; title=&#x22;&#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x201C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x201D;&#x8A34;&#x3048;&#x308B;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x300D;&#x300C;&#x3061;&#x3083;&#x3093;&#x3068;&#x4ED5;&#x4E8B;&#x3057;&#x3066;&#x308B;&#x4EBA;&#x306B;&#x306F;&#x90AA;&#x9B54;&#x300D;&#xFF08;&#x30B9;&#x30DD;&#x30CB;&#x30C1;&#x30A2;&#x30CD;&#x30C3;&#x30AF;&#x30B9;&#xFF09; - Yahoo!&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22;&#x3E;&#x3C;cite&#x3E;&#x3C;img src=&#x22;https://cdn-ak2.favicon.st-hatena.com/?url=https%3A%2F%2Fnews.yahoo.co.jp%2Farticles%2Ff9670349abd7ffffee0bd165054ff98051b42d4f&#x22; alt=&#x22;&#x22; /&#x3E; &#x3C;a href=&#x22;https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f&#x22;&#x3E;&#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x201C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x201D;&#x8A34;&#x3048;&#x308B;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x300D;&#x300C;&#x3061;&#x3083;&#x3093;&#x3068;&#x4ED5;&#x4E8B;&#x3057;&#x3066;&#x308B;&#x4EBA;&#x306B;&#x306F;&#x90AA;&#x9B54;&#x300D;&#xFF08;&#x30B9;&#x30DD;&#x30CB;&#x30C1;&#x30A2;&#x30CD;&#x30C3;&#x30AF;&#x30B9;&#xFF09; - Yahoo!&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x3C;/a&#x3E;&#x3C;/cite&#x3E;&#x3C;p&#x3E;&#x3C;a href=&#x22;https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f&#x22;&#x3E;&#x3C;img src=&#x22;https://cdn-ak-scissors.b.st-hatena.com/image/square/9288f987dcd248e074962bdda4e89fa8cc8641e2/height=90;version=1;width=120/https%3A%2F%2Famd-pctr.c.yimg.jp%2Fr%2Fiwiz-amd%2F20210611-00000299-spnannex-000-3-view.jpg&#x22; alt=&#x22;&#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x201C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x201D;&#x8A34;&#x3048;&#x308B;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x300D;&#x300C;&#x3061;&#x3083;&#x3093;&#x3068;&#x4ED5;&#x4E8B;&#x3057;&#x3066;&#x308B;&#x4EBA;&#x306B;&#x306F;&#x90AA;&#x9B54;&#x300D;&#xFF08;&#x30B9;&#x30DD;&#x30CB;&#x30C1;&#x30A2;&#x30CD;&#x30C3;&#x30AF;&#x30B9;&#xFF09; - Yahoo!&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; title=&#x22;&#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x201C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x201D;&#x8A34;&#x3048;&#x308B;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x300D;&#x300C;&#x3061;&#x3083;&#x3093;&#x3068;&#x4ED5;&#x4E8B;&#x3057;&#x3066;&#x308B;&#x4EBA;&#x306B;&#x306F;&#x90AA;&#x9B54;&#x300D;&#xFF08;&#x30B9;&#x30DD;&#x30CB;&#x30C1;&#x30A2;&#x30CD;&#x30C3;&#x30AF;&#x30B9;&#xFF09; - Yahoo!&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; class=&#x22;entry-image&#x22; /&#x3E;&#x3C;/a&#x3E;&#x3C;/p&#x3E;&#x3C;p&#x3E;&#x300C;2&#x3061;&#x3083;&#x3093;&#x306D;&#x308B;&#x300D;&#x5275;&#x8A2D;&#x8005;&#x306E;&#x897F;&#x6751;&#x535A;&#x4E4B;&#xFF08;&#x3072;&#x308D;&#x3086;&#x304D;&#xFF09;&#x6C0F;&#xFF08;44&#xFF09;&#x304C;11&#x65E5;&#x3001;&#x30A4;&#x30F3;&#x30BF;&#x30FC;&#x30CD;&#x30C3;&#x30C8;&#x653E;&#x9001;&#x306E;ABEMA&#x300C;ABEMA&#x3000;Prime&#x300D;&#xFF08;&#x6708;&#xFF5E;&#x91D1;&#x66DC;&#x5F8C;9&#x30FB;00&#xFF09;&#x306B;&#x30EA;&#x30E2;&#x30FC;&#x30C8;&#x51FA;&#x6F14;&#x3057;&#x300C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x300D;&#x3092;&#x5531;&#x3048;&#x308B;&#x5834;&#x9762;&#x304C;&#x3042;&#x3063;&#x305F;&#x3002; &#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x306F;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x3001;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x3093;&#x3067;&#x3059;&#x3088;&#x306D;&#x3002;&#x6587;&#x7AE0;&#x306B;&#x307E;&#x3068;&#x3081;&#x308B;&#x306E;&#x3063;&#x3066;&#x3001;&#x982D;&#x306E;&#x4E2D;&#x3067;&#x307E;&#x3068;&#x3081;&#x308B;&#x80FD;&#x529B;&#x3092;&#x4F7F;&#x3063;&#x3066;&#x3001;&#x8AAC;&#x660E;&#x6587;&#x3092;&#x66F8;...&#x3C;/p&#x3E;&#x3C;p&#x3E;&#x3C;a href=&#x22;https://b.hatena.ne.jp/entry/s/news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f&#x22;&#x3E;&#x3C;img src=&#x22;https://b.hatena.ne.jp/entry/image/https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f&#x22; alt=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF; - &#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x201C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x201D;&#x8A34;&#x3048;&#x308B;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x300D;&#x300C;&#x3061;&#x3083;&#x3093;&#x3068;&#x4ED5;&#x4E8B;&#x3057;&#x3066;&#x308B;&#x4EBA;&#x306B;&#x306F;&#x90AA;&#x9B54;&#x300D;&#xFF08;&#x30B9;&#x30DD;&#x30CB;&#x30C1;&#x30A2;&#x30CD;&#x30C3;&#x30AF;&#x30B9;&#xFF09; - Yahoo!&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; title=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF; - &#x3072;&#x308D;&#x3086;&#x304D;&#x6C0F;&#x201C;&#x96FB;&#x8A71;&#x4E0D;&#x8981;&#x8AD6;&#x201D;&#x8A34;&#x3048;&#x308B;&#x300C;&#x7ACB;&#x5834;&#x304C;&#x5F37;&#x304F;&#x3066;&#x7121;&#x80FD;&#x306A;&#x4EBA;&#x307B;&#x3069;&#x4F7F;&#x3044;&#x305F;&#x304C;&#x308B;&#x300D;&#x300C;&#x3061;&#x3083;&#x3093;&#x3068;&#x4ED5;&#x4E8B;&#x3057;&#x3066;&#x308B;&#x4EBA;&#x306B;&#x306F;&#x90AA;&#x9B54;&#x300D;&#xFF08;&#x30B9;&#x30DD;&#x30CB;&#x30C1;&#x30A2;&#x30CD;&#x30C3;&#x30AF;&#x30B9;&#xFF09; - Yahoo!&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; border=&#x22;0&#x22; style=&#x22;border: none&#x22; /&#x3E;&#x3C;/a&#x3E; &#x3C;a href=&#x22;https://b.hatena.ne.jp/entry/s/news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f&#x22;&#x3E;&#x3C;img src=&#x22;https://b.st-hatena.com/images/append.gif&#x22; border=&#x22;0&#x22; alt=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF;&#x306B;&#x8FFD;&#x52A0;&#x22; title=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF;&#x306B;&#x8FFD;&#x52A0;&#x22; /&#x3E;&#x3C;/a&#x3E;&#x3C;/p&#x3E;&#x3C;/blockquote&#x3E;</content:encoded>
            </item>
            <item rdf:about="https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html">
            <title>WEB&#x7279;&#x96C6; &#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#xFF1F;&#x77E5;&#x3089;&#x308C;&#x3056;&#x308B;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x306B;&#x8FEB;&#x3063;&#x305F; | NHK&#x30CB;&#x30E5;&#x30FC;&#x30B9;</title>
            <link>https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html</link>
            <description>&#x300C;&#x30B3;&#x30ED;&#x30CA;&#x798D;&#x3067;&#x3082;&#x307E;&#x3063;&#x305F;&#x304F;&#x4EBA;&#x304C;&#x8DB3;&#x308A;&#x3066;&#x3044;&#x306A;&#x3044;&#x3002;&#x63A1;&#x7528;&#x3082;&#x5897;&#x3048;&#x3066;&#x3044;&#x308B;&#x300D; &#x300C;&#x4ECA;&#x306E;&#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#x3002;2010&#x5E74;&#x4EE3;&#x306B;&#x306A;&#x3063;&#x3066;&#x7206;&#x767A;&#x7684;&#x306B;&#x62E1;&#x5927;&#x3057;&#x3066;&#x3044;&#x308B;&#x300D; &#x3044;&#x308F;&#x3086;&#x308B;&#x300C;&#x30AD;&#x30E3;&#x30EA;&#x30A2;&#x5B98;&#x50DA;&#x300D;&#x306E;&#x5FD7;&#x671B;&#x8005;&#x304C;&#x5E74;&#x3005;&#x3001;&#x6E1B;&#x5C11;&#x3059;&#x308B;&#x4E2D;&#x3001;&#x6771;&#x5927;&#x751F;&#x30FB;&#x4EAC;&#x5927;&#x751F;&#x306E;&#x5C31;&#x6D3B;&#x4EBA;&#x6C17;&#x30E9;&#x30F3;&#x30AD;&#x30F3;&#x30B0;&#x3067;&#x4E0A;&#x4F4D;&#x306E;&#x591A;&#x304F;&#x3092;&#x5360;&#x3081;&#x3066;&#x3044;&#x308B;&#x306E;&#x304C;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x3060;&#x3002; &#x5927;&#x4F01;&#x696D;&#x306E;&#x7D4C;&#x55B6;&#x6226;&#x7565;&#x3084;&#x5B98;&#x516C;&#x5E81;&#x306E;&#x653F;&#x7B56;&#x7ACB;&#x6848;&#x306B;&#x3082;...</description>
            <dc:date>2021-06-11T11:24:53Z</dc:date>
            <dc:subject>&#x5B66;&#x3073;</dc:subject>
            <dc:subject>&#x30B3;&#x30F3;&#x30B5;&#x30EB;</dc:subject>
            <dc:subject>&#x3042;&#x3068;&#x3067;&#x8AAD;&#x3080;</dc:subject>
            <dc:subject>&#x793E;&#x4F1A;</dc:subject>
            <dc:subject>&#x4ED5;&#x4E8B;</dc:subject>
            <dc:subject>&#x30AD;&#x30E3;&#x30EA;&#x30A2;</dc:subject>
            <dc:subject>&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x30BF;&#x30F3;&#x30C8;</dc:subject>
            <dc:subject>&#x52B4;&#x50CD;</dc:subject>
            <dc:subject>&#x696D;&#x754C;</dc:subject>
            <dc:subject>NHK</dc:subject>
            <taxo:topics>
              <rdf:Bag>
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%82%B3%E3%83%B3%E3%82%B5%E3%83%AB" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%81%82%E3%81%A8%E3%81%A7%E8%AA%AD%E3%82%80" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E7%A4%BE%E4%BC%9A" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E4%BB%95%E4%BA%8B" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%82%AD%E3%83%A3%E3%83%AA%E3%82%A2" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E3%82%B3%E3%83%B3%E3%82%B5%E3%83%AB%E3%82%BF%E3%83%B3%E3%83%88" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E5%8A%B4%E5%83%8D" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=%E6%A5%AD%E7%95%8C" />
                <rdf:li resource="https://b.hatena.ne.jp/search/tag?q=NHK" />
              </rdf:Bag>
            </taxo:topics>
            <hatena:bookmarkcount>421</hatena:bookmarkcount>
            <hatena:bookmarkCommentListPageUrl>https://b.hatena.ne.jp/entry/s/www3.nhk.or.jp/news/html/20210611/k10013078681000.html</hatena:bookmarkCommentListPageUrl>
            <hatena:imageurl>https://www3.nhk.or.jp/news/html/20210611/K10013078681_2106111904_2106111906_01_02.jpg</hatena:imageurl>
            <hatena:bookmarkSiteEntriesListUrl>https://b.hatena.ne.jp/site/www3.nhk.or.jp/</hatena:bookmarkSiteEntriesListUrl>
            <content:encoded>&#x3C;blockquote cite=&#x22;https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html&#x22; title=&#x22;WEB&#x7279;&#x96C6; &#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#xFF1F;&#x77E5;&#x3089;&#x308C;&#x3056;&#x308B;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x306B;&#x8FEB;&#x3063;&#x305F; | NHK&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22;&#x3E;&#x3C;cite&#x3E;&#x3C;img src=&#x22;https://cdn-ak2.favicon.st-hatena.com/?url=https%3A%2F%2Fwww3.nhk.or.jp%2Fnews%2Fhtml%2F20210611%2Fk10013078681000.html&#x22; alt=&#x22;&#x22; /&#x3E; &#x3C;a href=&#x22;https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html&#x22;&#x3E;WEB&#x7279;&#x96C6; &#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#xFF1F;&#x77E5;&#x3089;&#x308C;&#x3056;&#x308B;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x306B;&#x8FEB;&#x3063;&#x305F; | NHK&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x3C;/a&#x3E;&#x3C;/cite&#x3E;&#x3C;p&#x3E;&#x3C;a href=&#x22;https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html&#x22;&#x3E;&#x3C;img src=&#x22;https://cdn-ak-scissors.b.st-hatena.com/image/square/059dbbb55c472bedc74e94062cfb73d350553915/height=90;version=1;width=120/https%3A%2F%2Fwww3.nhk.or.jp%2Fnews%2Fhtml%2F20210611%2FK10013078681_2106111904_2106111906_01_02.jpg&#x22; alt=&#x22;WEB&#x7279;&#x96C6; &#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#xFF1F;&#x77E5;&#x3089;&#x308C;&#x3056;&#x308B;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x306B;&#x8FEB;&#x3063;&#x305F; | NHK&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; title=&#x22;WEB&#x7279;&#x96C6; &#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#xFF1F;&#x77E5;&#x3089;&#x308C;&#x3056;&#x308B;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x306B;&#x8FEB;&#x3063;&#x305F; | NHK&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; class=&#x22;entry-image&#x22; /&#x3E;&#x3C;/a&#x3E;&#x3C;/p&#x3E;&#x3C;p&#x3E;&#x300C;&#x30B3;&#x30ED;&#x30CA;&#x798D;&#x3067;&#x3082;&#x307E;&#x3063;&#x305F;&#x304F;&#x4EBA;&#x304C;&#x8DB3;&#x308A;&#x3066;&#x3044;&#x306A;&#x3044;&#x3002;&#x63A1;&#x7528;&#x3082;&#x5897;&#x3048;&#x3066;&#x3044;&#x308B;&#x300D; &#x300C;&#x4ECA;&#x306E;&#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#x3002;2010&#x5E74;&#x4EE3;&#x306B;&#x306A;&#x3063;&#x3066;&#x7206;&#x767A;&#x7684;&#x306B;&#x62E1;&#x5927;&#x3057;&#x3066;&#x3044;&#x308B;&#x300D; &#x3044;&#x308F;&#x3086;&#x308B;&#x300C;&#x30AD;&#x30E3;&#x30EA;&#x30A2;&#x5B98;&#x50DA;&#x300D;&#x306E;&#x5FD7;&#x671B;&#x8005;&#x304C;&#x5E74;&#x3005;&#x3001;&#x6E1B;&#x5C11;&#x3059;&#x308B;&#x4E2D;&#x3001;&#x6771;&#x5927;&#x751F;&#x30FB;&#x4EAC;&#x5927;&#x751F;&#x306E;&#x5C31;&#x6D3B;&#x4EBA;&#x6C17;&#x30E9;&#x30F3;&#x30AD;&#x30F3;&#x30B0;&#x3067;&#x4E0A;&#x4F4D;&#x306E;&#x591A;&#x304F;&#x3092;&#x5360;&#x3081;&#x3066;&#x3044;&#x308B;&#x306E;&#x304C;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x3060;&#x3002; &#x5927;&#x4F01;&#x696D;&#x306E;&#x7D4C;&#x55B6;&#x6226;&#x7565;&#x3084;&#x5B98;&#x516C;&#x5E81;&#x306E;&#x653F;&#x7B56;&#x7ACB;&#x6848;&#x306B;&#x3082;...&#x3C;/p&#x3E;&#x3C;p&#x3E;&#x3C;a href=&#x22;https://b.hatena.ne.jp/entry/s/www3.nhk.or.jp/news/html/20210611/k10013078681000.html&#x22;&#x3E;&#x3C;img src=&#x22;https://b.hatena.ne.jp/entry/image/https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html&#x22; alt=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF; - WEB&#x7279;&#x96C6; &#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#xFF1F;&#x77E5;&#x3089;&#x308C;&#x3056;&#x308B;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x306B;&#x8FEB;&#x3063;&#x305F; | NHK&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; title=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF; - WEB&#x7279;&#x96C6; &#x65E5;&#x672C;&#x3067;&#x552F;&#x4E00;&#x306E;&#x6210;&#x9577;&#x7523;&#x696D;&#xFF1F;&#x77E5;&#x3089;&#x308C;&#x3056;&#x308B;&#x201C;&#x30B3;&#x30F3;&#x30B5;&#x30EB;&#x696D;&#x754C;&#x201D;&#x306B;&#x8FEB;&#x3063;&#x305F; | NHK&#x30CB;&#x30E5;&#x30FC;&#x30B9;&#x22; border=&#x22;0&#x22; style=&#x22;border: none&#x22; /&#x3E;&#x3C;/a&#x3E; &#x3C;a href=&#x22;https://b.hatena.ne.jp/entry/s/www3.nhk.or.jp/news/html/20210611/k10013078681000.html&#x22;&#x3E;&#x3C;img src=&#x22;https://b.st-hatena.com/images/append.gif&#x22; border=&#x22;0&#x22; alt=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF;&#x306B;&#x8FFD;&#x52A0;&#x22; title=&#x22;&#x306F;&#x3066;&#x306A;&#x30D6;&#x30C3;&#x30AF;&#x30DE;&#x30FC;&#x30AF;&#x306B;&#x8FFD;&#x52A0;&#x22; /&#x3E;&#x3C;/a&#x3E;&#x3C;/p&#x3E;&#x3C;/blockquote&#x3E;</content:encoded>
            </item>
        """.trimIndent()

        val actual = HatebuRssParser.parse(rssString)
        val expected = Category(
            title = "はてなブックマーク - 人気エントリー - 総合",
            description = "最近の人気エントリー",
            url = "https://b.hatena.ne.jp/hotentry/all",
            articles = listOf(
                Article(
                    title = "ひろゆき氏“電話不要論”訴える「立場が強くて無能な人ほど使いたがる」「ちゃんと仕事してる人には邪魔」（スポニチアネックス） - Yahoo!ニュース",
                    url = "https://news.yahoo.co.jp/articles/f9670349abd7ffffee0bd165054ff98051b42d4f",
                    description = "「2ちゃんねる」創設者の西村博之（ひろゆき）氏（44）が11日、インターネット放送のABEMA「ABEMA　Prime」（月～金曜後9・00）にリモート出演し「電話不要論」を唱える場面があった。 ひろゆき氏は「立場が強くて、無能な人ほど使いたがるんですよね。文章にまとめるのって、頭の中でまとめる能力を使って、説明文を書...",
                    bookmarkCount = 479,
                    date = LocalDateTime.parse("2021-06-11T14:13:32Z", DateTimeFormatter.ISO_DATE_TIME),
                    imageUrl = "https://amd-pctr.c.yimg.jp/r/iwiz-amd/20210611-00000299-spnannex-000-3-view.jpg",
                ),
                Article(
                    title = "WEB特集 日本で唯一の成長産業？知られざる“コンサル業界”に迫った | NHKニュース",
                    url = "https://www3.nhk.or.jp/news/html/20210611/k10013078681000.html",
                    description = "「コロナ禍でもまったく人が足りていない。採用も増えている」 「今の日本で唯一の成長産業。2010年代になって爆発的に拡大している」 いわゆる「キャリア官僚」の志望者が年々、減少する中、東大生・京大生の就活人気ランキングで上位の多くを占めているのが“コンサル業界”だ。 大企業の経営戦略や官公庁の政策立案にも...",
                    bookmarkCount = 421,
                    date = LocalDateTime.parse("2021-06-11T11:24:53Z", DateTimeFormatter.ISO_DATE_TIME),
                    imageUrl = "https://www3.nhk.or.jp/news/html/20210611/K10013078681_2106111904_2106111906_01_02.jpg",
                ),
            )
        )
        assertEquals(2, actual.articles.size)
        assertEquals(expected.articles[0], actual.articles[0])
        assertEquals(expected.articles[1], actual.articles[1])
        assertEquals(expected, actual)
    }
}