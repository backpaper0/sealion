# Sealion

Java EE 7とDoma、ThymeleafでITSを作っています。

[![Circle CI](https://circleci.com/gh/backpaper0/sealion.svg?style=svg)](https://circleci.com/gh/backpaper0/sealion)

## 起動方法

Payara Microで動かす起動コマンドをGradleから実行できるようにしています。

次のコマンドを実行してください。

```
gradlew run
```

`Deployed 1 wars` というログが出力されたら起動完了です。
http://localhost:8080/sealion/ をブラウザで開いてください。

用意しているテストデータでは次のユーザーでログインできます。

|メールアドレス  |パスワード|
|----------------|----------|
|`root@localhost`|`root`    |

H2データベースをファイルモードで使用しています。
DBのファイルは `build` ティレクトリ以下に出力されます。
DBを作り直したい場合は `gradlew clean` してください。

## ビルド方法

次のコマンドを実行してください。

```
gradlew build
```

`/build/libs/sealion.war` が出力されます。

## URL

|URL|概要|
|---|----|
|`/`|プロジェクト一覧へリダイレクト|
|`/projects`|プロジェクト一覧|
|`/projects/new`|プロジェクト作成|
|`/projects/{project}`|タスク一覧へリダイレクト|
|`/projects/{project}/tasks`|タスク一覧|
|`/projects/{project}/tasks/new`|タスク作成|
|`/projects/{project}/tasks/{task}`|タスク詳細|
|`/projects/{project}/milestones`|マイルストーン一覧|
|`/projects/{project}/milestones/new`|マイルストーン作成|
|`/projects/{project}/milestones/{milestone}`|マイルストーン詳細|
|`/accounts`|アカウント一覧|
|`/accounts/new`|アカウント作成|
|`/accounts/{account}/edit`|アカウント編集|
|`/signin`|ログイン|

## 使用フレームワーク

* Java EE 7
* [Doma](https://github.com/domaframework/doma)
* [Thymeleaf](http://www.thymeleaf.org/)
* [Atlassian User Interface](https://docs.atlassian.com/aui/latest/)
* [Marked](https://github.com/chjj/marked)
* [Payara Micro](http://www.payara.fish/)

## License

Licensed under [The MIT License](https://opensource.org/licenses/MIT)
