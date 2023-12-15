# 情報実験II 山崎グループ B班 アプリケーションサーバ
主なコード共有はこちらで行います。

## 他のリポジトリ
[**クライアントサーバ**](https://github.com/BP21062/New_HighAndLow)

**アプリケーションサーバ**(ここ)

[**ロビーサーバ**](https://github.com/BP21062/HL_LobbyServer)


## ゲームについて

### システム定義
> 本システムは，4人でオリジナル要素が追加された「High&Low」を行うシステムである．
> 本システムは，クライアントとサーバ間で双方向通信を行い，リアルタイムでゲームの進行を行う。
> また，本システムは，データベースにユーザ情報やユーザ毎のゲームの戦績を保存し，ユーザは自分の戦績を閲覧することができる．

### ゲームのルール
<details>
  <summary>
    長くなるので折り畳み
  </summary>
  
### 主なルール
<br>
後でコピーする。
  
</details>

## 開発に関して
<img src="https://img.shields.io/badge/-intellij%20IDEA-000.svg?logo=intellij-idea&style=flat"> <img src="https://img.shields.io/badge/JDK-17-green">


### コーディング規約
| 内容 | 決定事項 |
| --- | --- |
| インデントの大きさ | タブ1つ |
| コメントのつけ方 | 全ての変数に//でつける |
| ドキュメント(Javadoc)のつけ方 | @author, @param, @return, @exception, ~~説明は後で決める~~ |
| フォルダ構成 | クライアントの画面だけフォルダを分け、他はmain直下に配置 |
| フォルダ命名規則 | class名を書く|
| 空白・ブロック | IntelliJの共有コードスタイルに沿う |

> [!NOTE]
> 分からないことはどんどん聞こう！

### Github運用ルール
| 内容 | 決定事項 |
| --- | --- |
| ブランチ戦略 | 機能実装ごとに、"GithubID/機能名"で作成。devから作成する |
| commitコメント・頻度 | リーダーが聞き返すことがないように。頻度は自由 |
| Pull Requests | 機能が完成したら行う。マージされたらブランチの削除を忘れずに。テンプレートは必要になったら作成する。マージはリーダーが行う |
| Issue作成方針 | これは必要になったら(通信周りが本格化したら)考える？ |
| 他 | タグ付け、レビュワー設定などはつけるつけない自由 |

### 実装ルール

| 内容 | 決定事項 | 
| --- | --- |
| テストケース | 通信を覗いてカバレッジ100%、Junitを使用する |
| 通信規約 | 作成したエクセルファイルに沿う。必要に応じて追加していく |

## 参考にしたリンク
- [イケてるレポジトリのREADME.mdには何を書くべきか](https://qiita.com/autotaker1984/items/bce70c8c67a8f6fb1b9d)
- [shields.ioを使って技術系アイコンを量産した](https://qiita.com/s-yoshiki/items/436bbe1f7160b610b05c)

## 後で見るリンク
- [Pull Requestsとブランチ保護(github.com)](https://docs.github.com/ja/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches/managing-a-branch-protection-rule)
- [Pull Requestsのテンプレート(github.com)](https://docs.github.com/ja/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository)
- [Issueのテンプレート(github.com)](https://docs.github.com/ja/communities/using-templates-to-encourage-useful-issues-and-pull-requests/configuring-issue-templates-for-your-repository)
