import org.junit.jupiter.api.AfterEach; // JUnitのAfterEachアノテーション：各テストの後にメソッドを実行するためのもの。
import org.junit.jupiter.api.BeforeEach; // JUnitのBeforeEachアノテーション：各テストの前にメソッドを実行するためのもの。
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver; // Selenium WebDriverのインターフェイス：ブラウザを操作するために使用。
import org.openqa.selenium.chrome.ChromeDriver; // Chromeブラウザ用のWebDriver実装。
import io.github.bonigarcia.wdm.WebDriverManager; // WebDriverManager：ドライバのセットアップを簡略化。


public class BaseTest { // テストの基盤となるクラス。
    protected WebDriver driver; // WebDriverをインスタンス化するための変数（子クラスからもアクセス可能）。
    private ScreenshotOnFailure watcher;

    @BeforeEach // 各テストメソッドが実行される前に実行されるメソッド。
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // ChromeDriverを自動的にセットアップしてくれるメソッド。
        driver = new ChromeDriver(); // Chromeブラウザのインスタンスを作成。
        watcher = new ScreenshotOnFailure(driver);

    }

    @AfterEach // 各テストメソッドが終了した後に実行されるメソッド。
    public void tearDown(TestInfo testInfo) {
        if (driver != null) { // driverがnullでない場合のみ終了処理を実行。
            if (testInfo.getTags().contains("fail_screenshot")) {
                watcher.testFailed(null, new Throwable("スクリーンショット撮影")); // 明示的に呼ぶ
            }
            driver.quit(); // ブラウザを閉じて、WebDriverのリソースを解放する
        }
    }
}

