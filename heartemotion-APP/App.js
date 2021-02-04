/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {PureComponent} from 'react';
import {StyleSheet} from 'react-native';

import Navgator from './app/pages/nav/Navgator';
export default class App extends PureComponent {
  render() {
    return <Navgator />;
  }
  componentDidMount() {
    Date.prototype.format = function (format) {
      var o = {
        'M+': this.getMonth() + 1, //month
        'd+': this.getDate(), //day
        'h+': this.getHours(), //hour
        'm+': this.getMinutes(), //minute
        's+': this.getSeconds(), //second
        'q+': Math.floor((this.getMonth() + 3) / 3), //quarter
        S: this.getMilliseconds(), //millisecond
      };
      if (/(y+)/.test(format)) {
        format = format.replace(
          RegExp.$1,
          (this.getFullYear() + '').substr(4 - RegExp.$1.length),
        );
      }
      for (var k in o) {
        if (new RegExp('(' + k + ')').test(format)) {
          format = format.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ('00' + o[k]).substr(('' + o[k]).length),
          );
        }
      }
      return format;
    };
  }
}

const styles = StyleSheet.create({});
