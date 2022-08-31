import React from "react";
import {View, Text, StyleSheet, ScrollView, TextInput, TouchableOpacity, Image} from "react-native";




export default function Comment({text, image}){
    return(
        <View style={styles.container}>
                <Image style={styles.image}
                       source={{uri: image}} />
            <View style={styles.comment}>
                <Text style={styles.textStyle}>
                    {text}
                </Text>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: "row",
        padding: 10,
    },
    comment: {
        maxWidth: '80%',
        fontSize: 13,
        padding: 15,
        borderRadius: 40,
        backgroundColor: "#eae9e9",
        alignSelf: "center",

    },
    image: {
        width: 30,
        height: 30,
        borderRadius: 15,
        margin: 10,
    },
    textStyle: {
        color: "black",
        textAlign: "center"
    }
})
