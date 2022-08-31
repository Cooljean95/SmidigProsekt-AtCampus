import React from "react";
import {View, Text, StyleSheet, ScrollView, TextInput, TouchableOpacity, Image} from "react-native";




export default function UsersComment({text, image}){
    return(
        <View style={styles.container}>
            <View style={styles.comment}>
                <Text style={styles.textStyle}>
                    {text}
                </Text>
            </View>
                <Image style={styles.image}
                       source={{uri: image}} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignSelf: "flex-end",
        flexDirection: "row",
        padding: 10,
    },
    comment: {
        maxWidth: '80%',
        fontSize: 13,
        padding: 12,
        borderRadius: 40,
        backgroundColor: "#3139ee",
        alignSelf: "center",

    },
    image: {
        width: 30,
        height: 30,
        margin: 10,
        borderRadius: 15,
    },
    textStyle: {
        color: "white",
        textAlign: "center"
    }
})
