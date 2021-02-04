import React from 'react';
import {Image} from 'react-native';

export default class HeightFixImage extends React.PureComponent {
  constructor() {
    super();
    this.state = {
      height: 0,
    };
  }
  render() {
    let {width} = this.props;
    if (this.state.height == 0) {
      return null;
    }
    return (
      <Image
        source={{uri: this.props.url}}
        style={{width, height: this.state.height}}
      />
    );
  }
  componentDidMount() {
    this.lordImage(this.props);
  }
  componentWillReceiveProps(nextProps, nextContext) {
    this.lordImage(nextProps);
  }
  lordImage(props) {
    let {url, width} = props;
    Image.getSize(url, (w, h) => {
      let ih = (h / w) * width;
      this.setState({
        height: ih ? ih : 0,
      });
    });
  }
}
