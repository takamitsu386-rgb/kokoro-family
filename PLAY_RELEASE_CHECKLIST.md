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

### 最新CI記録：Android build #509（2026-11-28）

- commit: `74220eff08b65bcc8aa293a2eb1bae5b64e16ee3`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46127612758
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15256571291`（12,192,212 bytes）
- debug APK artifact archive SHA-256: `b3faabe955a9fb538591b831d2bf51eeee77dd987aa12ebe4c086e77babdc5f9`
- 未署名Release AAB artifact ID: `15256476504`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `6a943dc1473e4db4c0d7a2825600ae07045b548b2be18b698496a31dd16c973e`
- artifact期限: 2027-02-26 08:49 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。

### 最新CI記録：Android build #510（2026-11-28）

- commit: `6a1d07852b0baa966d0e41bdbf27eaf4b1ed1dda`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46127921008
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15256273243`（12,192,214 bytes）
- debug APK artifact archive SHA-256: `881ccedfff5c03f8292c71b0c3e562180bae4787597de5c6544640ee37a3090d`
- 未署名Release AAB artifact ID: `15256516746`（8,592,613 bytes）
- 未署名Release AAB artifact archive SHA-256: `77433c4120c7589e47cb1e87c8948c35ad7c82ee1f6233602f60e17c8a93de1d`
- artifact期限: 2027-02-26 08:53 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #511（2026-11-28）

- commit: `ea2dfb8a81c8dc289a60a18ed073cc882f5a3c70`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46128159183
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15256497139`（12,192,216 bytes）
- debug APK artifact archive SHA-256: `4047b2cc06a1bb385203d8a391b6a885e39b990d0a70eaa51453542c81857807`
- 未署名Release AAB artifact ID: `15256548263`（8,592,611 bytes）
- 未署名Release AAB artifact archive SHA-256: `d0ecd68f6f8f435b0d00a385bec5175b7413c81649936638ea9e133c3de11356`
- artifact期限: 2027-02-26 08:56 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #511（2026-11-28）

- commit: `3fb3ba5545bb2f0448a0fec4fbde07b64224f5c9`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46128505544
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15257210989`（12,192,213 bytes）
- debug APK artifact archive SHA-256: `803e41ef0da60ae4206b41b8395792e4e4c9b2e0c15ca3b0d2bbc67e8491586e`
- 未署名Release AAB artifact ID: `15256259788`（8,592,617 bytes）
- 未署名Release AAB artifact archive SHA-256: `0ae449b289eb16f2f08590202d6102739bd5b76dfa04a40c78db4f2600283e3f`
- artifact期限: 2027-02-26 09:01 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #512（2026-11-28）

- commit: `8e7f4e604a456179cad932c780953801d76aa8ad`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46128832328
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15256752435`（12,192,215 bytes）
- debug APK artifact archive SHA-256: `f8e4a694f2c3c790504d100ec105ba875aeef40ed9e0940f316e04abcbff06a2`
- 未署名Release AAB artifact ID: `15256822968`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `b7b13eab25c03612db28b7a7971e8e755af1152df36208722e1ddf9ae595263e`
- artifact期限: 2027-02-26 09:05 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #513（2026-11-28）

- commit: `e0d91c77a8bce1d8cdfaa157ab474c07710186f1`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46129201764
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15256948184`（12,192,214 bytes）
- debug APK artifact archive SHA-256: `62fd97ea6ff5e17ff5f017c307e626adc2fc1668410f336f7aff1d0037c66490`
- 未署名Release AAB artifact ID: `15257192415`（8,592,612 bytes）
- 未署名Release AAB artifact archive SHA-256: `6091fd00109f9bc1342f5d3c9fa1c54473622c7a8163e8ba66c228fa477c4fb7`
- artifact期限: 2027-02-26 09:10 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #514（2026-11-28）

- commit: `98fb972d8446d237257be53eaf3d87a5a5cbc6a0`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46129512157
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15257068173`（12,192,218 bytes）
- debug APK artifact archive SHA-256: `15fa0594410ced0a1afd793842c26b3ec2e4ac0d51b389f3a1decc79bbefe53a`
- 未署名Release AAB artifact ID: `15256663754`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `01376392cfccdbb663e089123192b58711aafb2184c5f634c222a29437448a14`
- artifact期限: 2027-02-26 09:14 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #515（2026-11-28）

- commit: `195cb9397f11e483c1fc40f632a7bdf45dec93a8`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46129835657
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15257173531`（12,192,214 bytes）
- debug APK artifact archive SHA-256: `bafbadc83df2eb4db284f38b55d31e0ef6b38d32f796944a282bb49b2c9974dc`
- 未署名Release AAB artifact ID: `15257103977`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `b451bf9b148742a316e69e21e2e98dcc05e4134a80b030d8ac02a0b43cbd67ce`
- artifact期限: 2027-02-26 09:18 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #516（2026-11-28）

- commit: `e59e303ad13c3bd13f84f6243c6f6cd336d741ca`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46130142870
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15257139091`（12,192,216 bytes）
- debug APK artifact archive SHA-256: `addb56553559a0482b2886651b6e7eea8d4752b3934b87f741390e3a632497c6`
- 未署名Release AAB artifact ID: `15257845108`（8,592,611 bytes）
- 未署名Release AAB artifact archive SHA-256: `2271e25a7a666fd41515269b21f0514f28527bd89ab594f9a8a697cb7ae38505`
- artifact期限: 2027-02-26 09:22 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #517（2026-11-28）

- commit: `3b7945e37e52087467304c2664016cafe1109937`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46130434028
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15257338543`（12,192,214 bytes）
- debug APK artifact archive SHA-256: `af00140092f2dc84b3670fdfaf83233552c94551a3f0f37272785a149534a0b1`
- 未署名Release AAB artifact ID: `15257309449`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `2a738a194cf70e3efe487ab56553ff3d30e58cc2bb0827596f37c544f9acb820`
- artifact期限: 2027-02-26 09:25 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #518（2026-11-28）

- commit: `d1734c2dcebdcb4ee912b89037ebd982e1cfd791`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46132804247
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15258154545`（12,192,214 bytes）
- debug APK artifact archive SHA-256: `7eecf3e1bd4c1ff29fae5584da4cfb9dd8acc34d6c2c7dc555de226a726d73e7`
- 未署名Release AAB artifact ID: `15258875005`（8,592,610 bytes）
- 未署名Release AAB artifact archive SHA-256: `acb8947f21576834b6ae869960bf05fec37db8cc2dce93cd828a2ea485b820b6`
- artifact期限: 2027-02-26 09:56 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。



### 最新CI記録：Android build #519（2026-11-28）

- commit: `340ee9beb59eff64a241656f6f28317eddecc010`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46133094553
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体もsuccess。
- debug APK artifact ID: `15258588237`（12,192,219 bytes）
- debug APK artifact archive SHA-256: `65d6f2129be6268b0327effd646aff78feb0d1979e904c9e67a917aed868ccc7`
- 未署名Release AAB artifact ID: `15258751173`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `d0e56eb3ab53af1a23de264ba1dd160825a8fce3ef2611214c88e590d8aaf58c`
- artifact期限: 2027-02-26 10:00 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。



### 最新CI記録：Android build #520（2026-11-28）

- commit: `bea65ee93dbfd74989ff1ff46a0fd5fb2b9904e4`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46133630906
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。終了処理を含めGitHub Actions全体もsuccess。
- debug APK artifact ID: `15258941152`（12,192,218 bytes）
- debug APK artifact archive SHA-256: `7ff649a728e3433fcd0704b7c25fde3349e59c2f274c498ead7f700a0909c181`
- 未署名Release AAB artifact ID: `15259160746`（8,592,613 bytes）
- 未署名Release AAB artifact archive SHA-256: `884516acc298915b36663b485149151307f4d40d6d52fbd8ce5170f12d920b15`
- artifact期限: 2027-02-26 10:07 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #521（2026-11-28）

- commit: `5e9c5fe648d4fe69b808211c3cf3011bc3a1f0af`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46133979520
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体success。
- debug APK artifact ID: `15259470020`（12,192,217 bytes）
- debug APK artifact archive SHA-256: `48b5ae5d9589eee7aac8fdc0a64c9d06c33bda03e33ffaa78a86933fd27ddc63`
- 未署名Release AAB artifact ID: `15259216299`（8,592,611 bytes）
- 未署名Release AAB artifact archive SHA-256: `efe8a0e61ac30b6aa0a0edeab9ee7895c91b3caf35df543713f9c6b5a5f397d6`
- artifact期限: 2027-02-26 10:11 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。



### 最新CI記録：Android build #522（2026-11-28）

- commit: `76cb83c2b98d30904629d778292de19955b19a17`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46134540310
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体success。
- debug APK artifact ID: `15259216984`（12,192,212 bytes）
- debug APK artifact archive SHA-256: `978fb24ab28c590e824b285bca829328c23115d50af76a45bb26dad77836ee59`
- 未署名Release AAB artifact ID: `15258803150`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `a7dc3f97df22d45c0438e65636ddb3ad8908ce7afed2c50e6d5004e9f5cb7a63`
- artifact期限: 2027-02-26 10:18 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。



### 最新CI記録：Android build #523（2026-11-28）

- commit: `2d4aedbc2ba602dcd583087828498d9bc5c63b4e`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46134913212
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体success。
- debug APK artifact ID: `15259261567`（12,192,211 bytes）
- debug APK artifact archive SHA-256: `4b1ce5c88a628fde94fef86ccff19602c6c349668e1a3e2edc19ca6d6ed4c6ba`
- 未署名Release AAB artifact ID: `15259182933`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `98a6b4a9584ab555321c11b031ec1ef20220c978137d572e80716574744a0f40`
- artifact期限: 2027-02-26 10:22 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #524（2026-11-28）

- commit: `ab7ea34ffffaadf9620d40011af7233f916b345e`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46136102984
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。GitHub Actions全体success。
- debug APK artifact ID: `15259743513`（12,192,213 bytes）
- debug APK artifact archive SHA-256: `5043b202a2a0e6e57b2967ce8ca2be5c4b2f39f75deb418e1d17ee5fc5797cfa`
- 未署名Release AAB artifact ID: `15259313328`（8,592,610 bytes）
- 未署名Release AAB artifact archive SHA-256: `742785414a27471d64f5296c443a338b99ecc9936c0693b966c6b029c65cee59`
- artifact期限: 2027-02-26 10:38 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #525（2026-11-28）

- commit: `7d60e2b4fddee241b1ef21de5f7ea3c70191e7c1`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46136527788
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。終了処理を含めGitHub Actions全体success。
- debug APK artifact ID: `15259987345`（12,192,216 bytes）
- debug APK artifact archive SHA-256: `b0ddf7e51ba6e4f4811f90056497116ffa6030effb46066b423b4023d8819cd7`
- 未署名Release AAB artifact ID: `15260420781`（8,592,612 bytes）
- 未署名Release AAB artifact archive SHA-256: `b66444056837f6240f709fa6cf470b844d53cbd6361629b35e2197ba81cc285b`
- artifact期限: 2027-02-26 10:43 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #526（2026-11-28）

- commit: `a3a5684a925d0b9baccded100959ceebbe3ed047`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46136825748
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。終了処理を含めGitHub Actions全体success。
- debug APK artifact ID: `15260241806`（12,192,216 bytes）
- debug APK artifact archive SHA-256: `c71cd061f973b1f1e8c7acab33451dcd17b9f8d325b768033e329ab82bd57bf9`
- 未署名Release AAB artifact ID: `15259958803`（8,592,614 bytes）
- 未署名Release AAB artifact archive SHA-256: `345e2edd8cbc5bbe699e5b42da23913683c7f60651f0325ccc0df2f031c54ebe`
- artifact期限: 2027-02-26 10:47 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #527（2026-11-28）

- commit: `ac9dced9302712c0e54745f4e28cfe89e4d66523`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46137102156
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。終了処理を含めGitHub Actions全体success。
- debug APK artifact ID: `15260281990`（12,192,209 bytes）
- debug APK artifact archive SHA-256: `0a7242fb2daf1a9d4fe55bcee22ee139fb75af7c7048325131ff9378ef21f5ac`
- 未署名Release AAB artifact ID: `15259444115`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `7c6cd64b5db36aa456759fdf4a17134ee04b0c68427f67bcbbd128312d6677c3`
- artifact期限: 2027-02-26 10:51 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #528（2026-11-28）

- commit: `c56fd727bdf8a59cedfba15abe562d063278e31b`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46138330420
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。終了処理を含めGitHub Actions全体success。
- debug APK artifact ID: `15260523104`（12,192,217 bytes）
- debug APK artifact archive SHA-256: `30b2ee2a32aa6ca93892d0dbc74e4c2c4c9a0705d087b2cf85f5328dafbea7ed`
- 未署名Release AAB artifact ID: `15260483780`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `cadde71ac2ee5bf2fa6d20662dd6c07ca8a928798842990d725020a1cefcfad3`
- artifact期限: 2027-02-26 11:06 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #529（2026-11-28）

- commit: `3bdb621700b355b7d2c1ae58b4a2ecdb5d7ac446`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46138993853
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。終了処理を含めGitHub Actions全体success。
- debug APK artifact ID: `15261595305`（12,192,209 bytes）
- debug APK artifact archive SHA-256: `aa8d5e04074f38a40d55fbd051c30b10fa5c09d4f1cf29e5bdf030f813ca960e`
- 未署名Release AAB artifact ID: `15260618721`（8,592,615 bytes）
- 未署名Release AAB artifact archive SHA-256: `4c8c1718ecb3e8bc20e32b335a27b705442de07f73f62cc5b0fd7b135ddfa6b0`
- artifact期限: 2027-02-26 11:15 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。


### 最新CI記録：Android build #530（2026-11-28）

- commit: `5b861062d2fc87122ff8e87b63fac3161312c59b`
- 実行結果: https://github.com/takamitsu386-rgb/kokoro-family/actions/runs/46139250327
- 結果: lint成功、ユニットテスト成功、debug APK生成・アップロード成功、未署名Release AAB生成・アップロード成功。終了処理を含めGitHub Actions全体success。
- debug APK artifact ID: `15261132203`（12,192,213 bytes）
- debug APK artifact archive SHA-256: `a2cd59596aecea5922d3dd120e48c18b34a61b9aa7b22a27a6f6515bb7e93bb8`
- 未署名Release AAB artifact ID: `15260637734`（8,592,616 bytes）
- 未署名Release AAB artifact archive SHA-256: `e58adbecb8fa4cb8dce9634623a601325723d362569edd4ca61573714062eb6c`
- artifact期限: 2027-02-26 11:18 UTC
- 注意: CI成功は署名付きAABや実機動作の確認を意味しない。実機確認・署名鍵・公開準備の各項目は別途確認する。
