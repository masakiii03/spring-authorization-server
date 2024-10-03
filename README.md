# Spring Authorization Server
## 概要
このリポジトリは、Spring Authorization Serverを用いてOAuth2.0の認可サーバーを実装し、Spring Bootを使用したリソースサーバーを作成したプロジェクトです。OAuth2.0のClient Credentialsフローを使用して、セキュアにAPIを呼び出すサンプルプロジェクトです。

## 認可フロー
このサンプルではOAuth2.0のClient Credentialsフローをサポートしており、クライアントが認証情報(client_id, client_secret)を使ってトークンを取得し、リソースにアクセスします。

## テスト方法
1. リポジトリをクローンして、`spring-authorization-server-sample`と`resource-server-sample`を起動する。


2. 認可サーバーにクライアントを追加する。
```
curl -X POST http://localhost:9000/clients

# response
{"clientId":<client_id>,"clientSecret":<client_secret>}
```

3. 認可サーバーから認可トークンを取得する。
```
curl -X POST "http://localhost:9000/oauth2/token" -H "Content-Type: application/x-www-form-urlencoded" -u "<client_id>:<client_secret>" -d "grant_type=client_credentials&scope=read"

# response
{"access_token":"<token>","scope":"read","token_type":"Bearer","expires_in":599}
```

4. 取得したトークンを使用して、リソースサーバーのAPIを呼び出す。
```
curl http://localhost:8080 -H "Authorization: Bearer <token>"

# response
Successfully accessed the resource server with clientId: 23b413c4-7643-4358-8ce9-f17ba88f135c
```