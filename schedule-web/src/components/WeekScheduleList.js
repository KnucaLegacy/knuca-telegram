import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

import { Alert } from 'reactstrap';
import LessonTable from './LessonTable';
import LessonButtons from './LessonButtons';

export default class WeekScheduleList extends PureComponent {
    static propTypes = {
      lessonsState: PropTypes.shape({
        lessons: PropTypes.shape([]),
        isLoading: PropTypes.bool,
        isFetched: PropTypes.bool,
        searchItem: PropTypes.shape({}),
      }),
    }

    renderWeekOrNoSchedule = () => {
      const { lessons } = this.props.lessonsState;
      if (Object.keys(lessons).length !== 0) {
        return Object.values(lessons).map((day) => {
          const state = {
            ...this.props.lessonsState,
            lessons: day,
          };
          return <LessonTable lessonsState={state} key={state.lessons[0].date} />;
        });
      }
      return (
        <Alert color="primary">
        Розклад відсутній
        </Alert>
      );
    }
    render() {
      const {
        isLoading, isFetched, searchItem,
      } = this.props.lessonsState;

      if (isLoading || isFetched) {
        return (
          <div>
            {isLoading ? <LessonTable lessonsState={{
              isLoading, searchItem, lessons: [], isMany: true,
            }}
            /> : this.renderWeekOrNoSchedule()}
            <LessonButtons searchItem={searchItem} isDisabled={isLoading} />
          </div>
        );
      }
      return null;
    }
}
