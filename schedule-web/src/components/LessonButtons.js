import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { Button, Row, Col } from 'reactstrap';

import { fetchLessons } from '../actions/LessonsActions';
import getTommorowDate from '../utils/getTommorowDate';

export default class LessonButtons extends PureComponent {
  static propTypes = {
    searchItem: PropTypes.shape({
      type: PropTypes.string,
      id: PropTypes.string,
    }),
    isDisabled: PropTypes.bool,
  }

  onTodayClick = () => {
    const { searchItem } = this.props;
    const url = `/lessons/${searchItem.type}/${searchItem.id}`;
    fetchLessons(url, searchItem);
  }

  onTommorowClick = () => {
    const { searchItem } = this.props;
    const date = getTommorowDate();
    const url = `/lessons/${date}/${searchItem.type}/${searchItem.id}`;
    fetchLessons(url, searchItem, date);
  }

  onWeekClick = () => {
    const { searchItem } = this.props;
    const url = `/lessons/week/${searchItem.type}/${searchItem.id}`;
    fetchLessons(url, searchItem, undefined, true);
  }

  onNextWeekClick = () => {
    const { searchItem } = this.props;
    const url = `/lessons/week/1/${searchItem.type}/${searchItem.id}`;
    fetchLessons(url, searchItem, undefined, true);
  }

  render() {
    const { isDisabled } = this.props;
    return (
      <Row noGutters className="table-buttons">
        <Col>
          <Button
              outline
              block
              color="info"
              disabled={isDisabled}
              onClick={this.onTodayClick}
          >
             Сьогодні
          </Button>
        </Col>
        <Col>
          <Button
              outline
              block
              color="info"
              disabled={isDisabled}
              onClick={this.onTommorowClick}
          >
            Завтра
          </Button>
        </Col>
        <Col>
          <Button
              outline
              block
              color="info"
              disabled={isDisabled}
              onClick={this.onWeekClick}
          >
            Тиждень
          </Button>
        </Col>
        <Col>
          <Button
              outline
              block
              color="info"
              disabled={isDisabled}
              onClick={this.onNextWeekClick}
          >
            Наст. тиждень
          </Button>
        </Col>
      </Row>
    );
  }
}
