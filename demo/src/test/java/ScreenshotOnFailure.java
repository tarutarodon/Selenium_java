import org.junit.jupiter.api.extension.ExtensionContext; // JUnitのExtensionContextクラス。テストのコンテキスト情報を管理。
import org.junit.jupiter.api.extension.TestWatcher; // JUnitのTestWatcherインターフェイス。テストの成功や失敗時の動作をカスタマイズ可能。
import org.openqa.selenium.OutputType; // SeleniumのOutputTypeクラス。スクリーンショットなどのデータ出力形式を指定。
import org.openqa.selenium.TakesScreenshot; // SeleniumのTakesScreenshotインターフェイス。スクリーンショット撮影を可能にする。
import org.openqa.selenium.WebDriver; // SeleniumのWebDriverインターフェイス。ブラウザ操作の基盤となるクラス。

import java.io.File; // JavaのFileクラス。ファイルの操作を扱う。
import java.nio.file.Files; // JavaのFilesクラス。ファイルやディレクトリ操作のユーティリティ。
import java.nio.file.Path; // JavaのPathクラス。ファイルやディレクトリのパスを表現。
import java.nio.file.StandardCopyOption; // JavaのStandardCopyOptionクラス。ファイルコピー時のオプション指定。
import java.util.Optional; // JavaのOptionalクラス。値が存在するかどうかを管理。

public class ScreenshotOnFailure implements TestWatcher { // テスト失敗時にスクリーンショットを撮影するクラス。TestWatcherを実装。

    private final WebDriver driver; // WebDriverインスタンス（ブラウザ操作用）。
        
    // コンストラクタ: WebDriverを受け取り初期化
    public ScreenshotOnFailure(WebDriver driver) {
        this.driver = driver;
    }
    
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) { // テスト失敗時に呼び出されるメソッド
        try {
            // スクリーンショットを撮影（TakesScreenshotインターフェイスをキャストして使用）
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String testName = context.getDisplayName(); // テスト名を取得（ファイル名に使用）
            Path destination = Path.of("screenshots", testName + ".png"); // 保存先のパスを指定

            // 保存先ディレクトリを作成（必要であれば）
            Files.createDirectories(destination.getParent());
            // スクリーンショットファイルを指定した場所へコピー
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            // 保存場所をコンソールに出力
            System.out.println("Screenshot saved: " + destination);
        } catch (Exception e) { // 例外発生時の処理
            e.printStackTrace(); // スタックトレースを出力
        }

        System.out.println("This method is over!!");
        System.out.println("This method is over!!");
        System.out.println("This method is over!!");
    }

    @Override
    public void testSuccessful(ExtensionContext context) { // テスト成功時に呼び出されるメソッド
        // 成功時の追加処理を記述（今回は何もしない）
    }
}
