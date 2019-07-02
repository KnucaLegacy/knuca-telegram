import React from 'react';
import { Container, Row, Col, Button } from 'reactstrap';
import Image from './Image';
import images from '../img';

const News = () => (
  <section className="news-container">
    <Container>
      <Row className="news-item upper">
        <Col xl={6} lg={7} md={12} sm={12} className="upper-left">
          <Image
              xl={images.xl.benefitsImage}
              lg={images.lg.benefitsImageLg}
              md={images.md.benefitsImageMd}
              sm={images.sm.benefitsImageSm}
              alt="Benefits"
          />
        </Col>
        <Col xl={{ size: 5, offset: 1 }} lg={5} md={12} sm={12} className="upper-right">
          <Image
              xl={images.xl.monitorImage}
              lg={images.lg.monitorImageLg}
              sm={images.sm.monitorImageSm}
              alt="Search monitor"
          />
        </Col>
      </Row>
      <Row className="news-item mid">
        <Col xl={7} lg={7} md={12} sm={12} className="mid-left">
          <Image
              xl={images.xl.tgPhonesImage}
              lg={images.lg.tgPhonesImageLg}
              sm={images.sm.tgPhonesImageSm}
              alt="Telegram bot on phones"
          />
        </Col>
        <Col xl={5} lg={5} md={12} sm={12} className="mid-right">
          <div className="text-block">
            <h2>Легко, швидко і точно</h2>
            <p>Телеграм бот відповість</p>
            <p>на всі ваші запити</p>
            <span>@Knuca_ScheduleBot</span>
            <Button
                size="md"
                color="info"
                tag="a"
                href="https://t.me/KNUCA_ScheduleBot"
                className="text-block-link"
            >
              Приєднуйся
            </Button>
          </div>
        </Col>
      </Row>
      <Row className="news-item low">
        <Col xl={6} lg={7} md={12} sm={12} className="low-left">
          <div className="text-block">
            <h2>Особливості та переваги</h2>
            <p>Бот дозволяє здійснювати пошук</p>
            <p>за групою або викладачем</p>
            <p>Також ви можете вказати дату</p>
            <p>або діапазон дат</p>
          </div>
        </Col>
        <Col xl={6} lg={{ size: 5, offset: 0 }} md={12} sm={12}className="low-right">
          <Image
              xl={images.xl.tgScreenshots}
              lg={images.lg.tgScreenshotsLg}
              sm={images.sm.tgScreenshotsSm}
              alt="Telegram screenshots"
          />
        </Col>
      </Row>
    </Container>
  </section>
);

export default News;
