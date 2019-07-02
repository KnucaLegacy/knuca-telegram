import React from 'react';
import { Container, Row, Col } from 'reactstrap';
import Image from './Image';
import images from '../img';

const Footer = () => (
  <footer>
    <Container>
      <Row>
        <Col className="page-footer">
          <div className="footer-credits">
            <Image
                xl={images.xl.KnucaLegacyLogo}
                lg={images.lg.KnucaLegacyLogoLg}
                sm={images.sm.KnucaLegacyLogoSm}
                alt="Knuca Legacy logo"
            />
            <span>&copy; Knuca Legacy 2018, All rights reserved.</span>
          </div>
          <div className="footer-contacts">
            <span>Зв`яжіться з нами</span>
            <a href="mailto:mark.shulhin@gmail.com">
              <Image
                  xl={images.xl.gmailLogo}
                  lg={images.lg.gmailLogoLg}
                  sm={images.sm.gmailLogoSm}
                  alt="Gmail logo"
              />
            </a>
            <a href="https://t.me/KNUCA_ScheduleBot">
              <Image
                  xl={images.xl.tgLogo}
                  lg={images.lg.tgLogoLg}
                  sm={images.sm.tgLogoSm}
                  alt="Knuca Legacy logo"
              />
            </a>
          </div>
        </Col>
      </Row>
    </Container>
  </footer>
);

export default Footer;
