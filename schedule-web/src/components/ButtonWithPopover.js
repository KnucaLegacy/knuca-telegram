import React, { Component } from 'react';
import {
  Button,
  Popover,
  PopoverHeader,
  PopoverBody,
} from 'reactstrap';


export default class ButtonWithPopover extends Component {
    state = {
      popoverOpen: false,
    }

    toggle = () => {
      this.setState({
        popoverOpen: !this.state.popoverOpen,
      });
    }
    render() {
      return (
        <React.Fragment>
          <Button
              color="primary"
              id="popover"
              onClick={this.toggle}
          >
            <span className="oi oi-magnifying-glass" />
          </Button>
          <Popover
              target="popover"
              placement="left"
              isOpen={this.state.popoverOpen}
              toggle={this.toggle}
          >
            <div className="popover-arrow" />
            <PopoverHeader>Пошук розкладу</PopoverHeader>
            <PopoverBody>
              <div style={{ marginBottom: '10px' }}>
                Введіть назву группи, аудиторії або ім`я викладача,
                 натисніть на необхідий стовпчик та отримайте розклад на сьогодні.
              </div>
              <Button
                  color="danger"
                  onClick={this.toggle}
              >
              Зрозуміло
              </Button>
            </PopoverBody>
          </Popover>
        </React.Fragment>
      );
    }
}
