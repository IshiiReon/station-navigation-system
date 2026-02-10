# 駅構内ナビゲーションシステム

駅構内での移動を、初めて利用する人や外国人でも
分かりやすく案内することを目的とした Web アプリケーションです。

出発地点と目的地をノードとエッジでモデル化し、
最短経路探索アルゴリズムを用いてルートを算出します。

出発地点と目的地を選択すると、
構内の最短経路と案内用画像を表示します。

---

##  制作背景・目的
- 駅構内は構造が複雑で迷いやすい
- 外国人利用者や初めて来る人でも直感的に使える案内を作りたい
- 学習した **Java / Servlet / DB** を使った実践的な作品として制作

---

##  使用技術
- Java  
- JSP / Servlet  
- JDBC  
- H2 Database  
- Apache Tomcat 10  
- HTML / CSS  

---

##  機能概要
- 出発地点・到着地点の選択
- 駅構内ノード情報の取得
- 最短経路計算
- 経路に対応した案内画像の表示

---

##  工夫・こだわりポイント
- 駅構内をノード構造として設計し、拡張しやすいデータ構成にした
- MVC構成を意識し、処理と表示の役割を分離
- 利用者が迷わないよう、案内画像とルート表示を組み合わせたUIを設計

## 🖼 画面イメージ

<img src="screen_images/title_sample.png" alt="駅構内ナビ タイトル" width="700">
<img src="screen_images/result_sample1.png" alt="駅構内ナビ 結果画面１" width="700">
<img src="screen_images/result_sample2.png" alt="駅構内ナビ 結果画面１" width="700">
<img src="screen_images/result_sample3.png" alt="駅構内ナビ 結果画面１" width="700">




## ▶ 起動方法（ローカル環境）

1. このリポジトリをクローン
```bash
git clone https://github.com/IshiiReon/station-navigation-system.git
