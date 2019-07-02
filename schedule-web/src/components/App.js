import React from 'react';
import { Container, Row, Col } from 'reactstrap';

import LessonsContainer from '../containers/LessonsContainer';
import SearchContainer from '../containers/SearchContainer';
import News from './News';
import Footer from './Footer';

import '../css/App.css';
import '../css/Grid.css';
import '../css/media-lg.css';
import '../css/media-md.css';
import '../css/media-sm.css';

const App = () => (
  <main id="main">
    <section id="search-bg">
      <Container>
        <Row>
          <Col className="search-container">
            <SearchContainer />
            <LessonsContainer />
          </Col>
        </Row>
      </Container>
    </section>
    <News />
    <Footer />
  </main>
);


export default App;
