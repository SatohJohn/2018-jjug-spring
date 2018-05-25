# 2018-jjug-spring
2018 JJUG CCC Springで利用したサンプルソースコードです

## 動作環境
以下アプリケーションがlocalhostで起動している前提です。

* Netflix Eureka
    * port: 11801
* Apache Kafka
    * port: 9092

### Spring Cloud Configに関して
* 起動場所はlocalhostでなくて良いです
* アプリケーション名は「config-test」としてください。
* configがアクセスするGithubレポジトリ、または他のレポジトリに「my-id-prod.properties」がmasterブランチにあることが必要です
