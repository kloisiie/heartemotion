// MapView.js
import PropTypes from 'prop-types';
import React from 'react';
import {requireNativeComponent, UIManager, findNodeHandle} from 'react-native';

class HeartRateChart extends React.Component {
  render() {
    return <HeartRateChartComponent {...this.props} />;
  }

  componentWillUnmount() {}
}

HeartRateChart.propTypes = {
  /**
   * A Boolean value that determines whether the user may use pinch
   * gestures to zoom in and out of the map.
   */
};

const HeartRateChartComponent = requireNativeComponent(
  'HeartRateChartComponent',
  HeartRateChart,
);

export default HeartRateChart;
