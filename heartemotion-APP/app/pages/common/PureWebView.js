/*
 * 此组件只用于单纯的网页显示，如果设计复杂交互，请务修改此组件，另外封装使用
 *
 * */
import React, {PureComponent} from 'react';
import {View} from 'react-native';
import WebView from 'react-native-webview';
export default class PureWebView extends PureComponent {
  constructor(props) {
    super(props);
    let {title} = this.props.navigation.state.params;
    this.state = {
      canGoBack: false,
      title: title,
    };
  }
  static navigationOptions = ({navigation}) => {
    if (navigation.state.params.navigation) {
      return navigation.state.params.navigation;
    }
    return {
      headerTitle: '加载中...',
    };
  };

  updateNavigation(title) {
    this.props.navigation.setParams({
      navigation: {
        headerTitle: 'title',
      },
    });
  }

  render() {
    let url = this.props.navigation.state.params.url;
    let html = this.props.navigation.state.params.html;
    let source = null;
    if (url) {
      source = {uri: url, headers: {'Cache-Control': 'no-cache'}};
    }
    if (html) {
      source = {html: html};
    }
    return (
      <View style={{flex: 1}}>
        <WebView
          source={source}
          style={{flex: 1}}
          ref={'webview'}
          onNavigationStateChange={this._onNavigationStateChange.bind(this)}
          startInLoadingState={true}
        />
      </View>
    );
  }
  goback() {
    if (this.state.canGoBack) {
      this.refs.webview.goBack();
    } else {
      this.props.navigation.goBack();
    }
  }
  //获取页面title，URL，loading, canGoBack, canGoForward
  _onNavigationStateChange(navState) {
    this.setState({
      canGoBack: navState.canGoBack,
    });
    if (navState && navState.title) {
      console.log('PureWebView', navState);
      console.log('PureWebView', this.state.title);
      let title = Math.abs(navState.title.indexOf('http'))
        ? navState.title
        : this.title;
      if (this.state.title) {
        this.props.navigation.setParams({
          navigation: {
            headerTitle: this.state.title,
          },
        });
      } else if (title && title.length > 0 && !title.includes('/')) {
        this.props.navigation.setParams({
          navigation: {
            headerTitle: title,
          },
        });
      }
    }
  }

  componentWillUnmount() {
    let callback = this.props.navigation.state.params.callback;
    if (callback) {
      callback();
    }
  }
}
