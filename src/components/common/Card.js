import React, {Component} from 'react';
import {View, Text} from 'react-native';

const Card = (props) => {
    return(
        <View style={styles.containerStyle}>
           {props.children}
        </View>
    );
}

const styles = {
    containerStyle: {
        boderWidth: 1,
        borderRadius: 8,
        borderColor: '#ddd',
        backgroundColor: '#fff',
        borderBottomWidth: 0,
        elevation: 2,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 4 },
        shadowOpacity: 0.1,
        shadowRadius: 8,
        margin: 20
    }
}


export { Card };