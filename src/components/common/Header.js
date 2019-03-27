import React from 'react';
import {View, Text} from 'react-native';

const Header = (props) => {
    
    const {textStyle, viewStyle} = styles;
    
    return (
        <View style={viewStyle}>
            <Text style={textStyle}>{props.title}</Text>
        </View>
    );
};

const styles = {
    viewStyle: {
        backgroundColor: '#f8f8f8',
        elevation: 4,
        justifyContent: 'center',
        alignItems: 'center',
        height: 60,
        padding: 20,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2},
        shadowOpacity: 0.2,
        position: 'relative'
    },
    textStyle: {
        fontSize: 20,
        color: '#000'
    }
};

export { Header };
