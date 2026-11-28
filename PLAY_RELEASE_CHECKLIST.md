# Google Play 公開前チェックリスト

> この文書は公開準備の確認用です。チェック項目を実際に確認するまで完了扱いにしないでください。Google Play Consoleの最新の案内を優先し、保護者などの成人と一緒に進めてください。

## 1. アプリとビルド

- [ ] 最新のAndroid build validationが成功している
- [ ] 署名付きRelease AABを作成できる
- [ ] AABのパッケージ名が `jp.hiyori.app` である
- [ ] バージョンコード・バージョン名を確認した
- [ ] Pixel 9など実際に対象とする端末でインストール・起動した
- [ ] AIが利用できない場合の案内を確認した
- [ ] テキスト会話、音声入力、読み上げ、連続会話を実機で確認した
- [ ] 履歴・設定・下書き・バックアップの保存と復元を確認した
- [ ] アプリのアンインストールやデータ消去時の保存データの扱いを確認した

## 2. 署名鍵の安全管理

- [ ] 保護者など信頼できる成人と署名鍵を作成・保管した
- [ ] キーストア原本と復旧用コピーを安全な場所に保管した
- [ ] GitHub Actions Secretsに必要な値だけを登録した
- [ ] 署名鍵・パスワードをチャットやソースコードへ貼り付けていない
- [ ] 同じアプリの更新で同じ署名鍵を継続使用できることを確認した

## 3. プライバシーとデータセーフティ

- [ ] `PRIVACY_POLICY_DRAFT.md` の運営者名・問い合わせ先・公開日を記入した
- [ ] 実際のコード、SDK、音声認識サービスの挙動とポリシーの記述を照合した
- [ ] ポリシーを一般公開できるURLに掲載した
- [ ] Google Play Consoleのプライバシーポリシー欄にURLを登録した
- [ ] データセーフティ申告を、実際の収集・共有・処理内容に基づいて回答した
- [ ] 音声認識サービス側の通信・データ取り扱いを確認した
- [ ] ユーザーが共有・書き出しを行う場合の説明を確認した
- [ ] データ削除・問い合わせ方法が実際に機能することを確認した

## 4. ストア掲載情報

- [ ] `STORE_LISTING_DRAFT.md` の説明を実際のアプリ機能と照合した
- [ ] アプリ名・短い説明・詳しい説明を確定した
- [ ] 「完全オフライン」「全端末対応」「無制限」など誤解を招く表現がない
- [ ] Gemini Nanoの対応条件と初回モデル準備について説明した
- [ ] 音声認識がAndroidのサービスに依存することを説明した
- [ ] `STORE_SCREENSHOT_PLAN.md` に沿って実機スクリーンショットを撮影した
- [ ] スクリーンショットに個人情報・通知・実画面にない機能が写っていない

## 5. 対象年齢・コンテンツ・ポリシー

- [ ] 対象年齢とコンテンツレーティングの質問に、実際の機能・会話内容に基づいて回答した
- [ ] 子どもを対象に含めるかどうかを、保護者とGoogle Playの最新要件を確認して決めた
- [ ] 広告・アプリ内購入・外部リンクの有無を実装と照合して申告した
- [ ] アプリの説明、プライバシーポリシー、データセーフティ申告に矛盾がない
- [ ] 必要な場合はGoogle Playのアプリ審査用アクセス情報や説明を準備した

## 6. 最終公開判断

- [ ] 保護者などの成人が掲載内容と申告内容を確認した
- [ ] 未確認の実機機能を確認済みと表示していない
- [ ] 審査提出するAABが署名付きの正式Releaseビルドである
- [ ] 公開範囲・公開時期・段階的公開の設定を確認した

## 現在わかっている状態

- Androidアプリの `compileSdk` / `targetSdk` は36
- アプリIDは `jp.hiyori.app`
- 現在のバージョンは `0.13.65`（versionCode 114）
- GitHub Actionsでdebug APKと未署名Release AABを作成する構成
- 署名付きRelease AAB用の専用ワークフローを用意
- 2026-11-28のAndroid build #499（commit `145a977eab1a2e9b48ce17be6bfbc40cc319acfc`）はlint・ユニットテスト・debug APK・未署名AABの全工程が成功
- build #499のdebug APK artifact ID: `15254550381`、未署名AAB artifact ID: `15253929894`
- build #499のdebug APK artifact SHA-256: `61d33cc4e38dbaaf1dedce5baaf78fa9c802d9965acac901d4b8fd556a34dbd4`
- build #499の未署名AAB artifact SHA-256: `b1286d220db18ac08e8f36b6485dfab44dae85e356a0728708daa9a78fc038f5`
- Pixel 9実機でのインストール・動作確認は未完了（CI成功とは別に実機確認が必要）
- プライバシーポリシーは確認用ドラフトで、公開用として未確定
- ストア説明文とスクリーンショット計画はドラフト
- 2026-11-28のAndroid build #500（commit `d43beba869772038b59d9f230c994fb32cc7f050`）もlint・ユニットテスト・debug APK・未署名AABの全工程が成功
- build #500の実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46126003857
- build #500のdebug APK artifact ID: `15255971478`（SHA-256: `dec9509bcb97dee0cae71323fb30b88c1131b0f6dd0a9d2e15bd5c708e9b666c`）
- build #500の未署名AAB artifact ID: `15255627705`（SHA-256: `836317c1b505be63d71ddcef106b0b2ab6924cafa70c66a83a96dc1d37638420`）
- 2026-11-28のAndroid build #501（commit `127189e3f606c339749398ed471426ec59d806fe`）もlint・ユニットテスト・debug APK・未署名AABの全工程が成功
- build #501の実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46126407702
- build #501のdebug APK artifact ID: `15255822399`（SHA-256: `4f6441081329c556c6d67d4ed821739699879205bbc790fd84c9ce06e9393917`）
- build #501の未署名AAB artifact ID: `15255917273`（SHA-256: `552b3892d2b5f5e0aa1457bb90d74865a5b3d510942617ae591e280a32ff0b5d`）


### 最新CI記録：Android build #507（2026-11-28）

- commit: `97a66638f7dc7d28fdbd7f7581c4fb5dc5b870ad`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46127009501
- 結果: lint成功、ユニットテスト12件成功、debug APK生成成功、未署名Release AAB生成成功
- debug APK artifact ID: `15256565377`（12,192,215 bytes）
- debug APK artifact archive SHA-256: `f109baa7e87adcfddaff1c919af94dc811b1baa45870656d1942c66f695b7c57`
- 未署名Release AAB artifact ID: `15255389701`（8,592,614 bytes）
- 未署名Release AAB artifact archive SHA-256: `3858f111f1aa1da2e13116455e4c0d136dbd01a7493b26d33f0a02f55080ed0d`
- artifact期限: 2027-02-26 08:41 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #508（2026-11-28）

- commit: `37999768007e21fc05a2acfb57b58397722ac3db`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46127337496
- 結果: lint成功、ユニットテスト12件成功、debug APK生成成功、未署名Release AAB生成成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15256656292`（12,192,215 bytes）
- debug APK artifact archive SHA-256: `956435dcc0f0743bbfc2585d09fa5b511d21a9ba75bbe403fbbb87a070fdaa3c`
- 未署名Release AAB artifact ID: `15255794466`（8,592,611 bytes）
- 未署名Release AAB artifact archive SHA-256: `30a90083bcc7565cbd407881f96f90c03d3750d3c1b3d99ca30d3e666cb06429`
- artifact期限: 2027-02-26 08:46 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。
