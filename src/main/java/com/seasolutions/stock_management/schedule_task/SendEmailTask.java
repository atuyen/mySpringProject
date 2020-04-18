package com.seasolutions.stock_management.schedule_task;


import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SendEmailTask {

    //    3.1. Chạy lặp (Sử dụng fixedRate)
    //    sau khi deploy, cứ mỗi ngày cần phải gửi báo cáo về số lượng người mua hàng tới email của nhà bán hàng.
    //    Như vậy, ta có thể sử dụng schedule với fixedRate là 84600 (Trong đoạn code dưới đây, để là 2000ms để ta có thể nhìn dễ hơn )
//    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
        // call send email method here
        log.info("Send email to producers to inform quantity sold items");
    }




    //    Cùng với bài toán ở phía trên, nhưng thay đổi 1 chút.
    //    Đó là thay vì mỗi giờ bạn gửi email, thì giờ bạn muốn thay đổi là, khi tác vụ gửi email đó hoàn thành, 1 tiếng sau đó, lại thực hiện lại tác vụ gửi email.
    //    Cứ như thế... Nó khác gì với yêu cầu thứ phía trên? Khác là ở chỗ

    //Ở 3.1, nếu bạn deploy app thành công lúc 12h, thì tác vụ gửi mail sẽ được gọi đầu tiên lúc 12h trưa, sau đó 1h, 2h, 3h .v.v.
    // là những mốc thời gian mà tác vụ gửi mail đc thực hiện

    //Ở 3.2, khác 1 chút, vẫn là 12h deploy thành công, 12h thực hiện tác vụ gửi mail đầu tiên.
    // Nhưng nếu việc gửi mail mất 5p, thì 1 tiếng sau đó (tức là 1h5p) thì tác vụ gửi mail mới dc gọi lần thứ 2,
    // lần này gửi mail lại mất 10p (tức là 1h15p ms hoàn thành) thì phải chờ tới 2h15p mới được gọi lại

//    @Scheduled(fixedDelay = 10000)
    public void scheduleTaskWithFixedDelay() {
        // Call send email method here
        // Pretend it takes 1000ms
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Send email to producers to inform quantity sold items");
    }





    //Chạy lặp với khoảng thời gian fixedRate sau khi đi deploy initialDelay (Sử dụng kết hợp fixedRate và initialDelay)
    //Gần giống như 3.1, Ở đây chỉ khác là, nếu như 3.1 bạn chạy tác vụ gửi mail ngay khi deploy xong,
    // thì initialDelay cho phép bạn thực hiện việc này sau 1 khoảng thời gian là initialDelay(miliseconds)
//    @Scheduled(fixedRate = 2000, initialDelay = 10000)
    public void scheduleTaskWithInitialDelay() {
        log.info("Send email to producers to inform quantity sold items");
    }






    //Cũng vẫn yêu cầu gửi mail, nhưng bạn muốn gửi vào 12h thứ 6 hàng tuần, hoặc 23h59 ngày cuối tháng .v.v.v.
    // Những thứ bên trên kia là ko đủ. Vậy bạn hãy nghĩ tới cron. Mình ví dụ, log ra màn hình vào giây 15 của mỗi phút. Ta làm như sau
    //https://www.freeformatter.com/cron-expression-generator-quartz.html
//    @Scheduled(cron = "15 * * * * ?")
    public void scheduleTaskWithCronExpression() {
        log.info("Send email to producers to inform quantity sold items");
    }










}
