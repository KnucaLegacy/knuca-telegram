import React, { Component } from 'react';
import { Container } from 'flux/utils';
import 'core-js/es7/object';

import LessonTable from '../components/LessonTable';
import WeekScheduleList from '../components/WeekScheduleList';

import LessonsStore from '../stores/LessonsStore';

class LessonsContainer extends Component {
  static getStores() {
    return [
      LessonsStore,
    ];
  }

  static calculateState() {
    const lessonsState = LessonsStore.getState();

    return {
      lessonsState,
    };
  }

  render() {
    if (this.state.lessonsState.isMany) {
      return <WeekScheduleList lessonsState={this.state.lessonsState} />;
    }
    return (
      <React.Fragment>
        <LessonTable {...this.state} />
      </React.Fragment>
    );
  }
}


export default Container.create(LessonsContainer);
