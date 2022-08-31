import {StyleSheet, View, Image, Text, TouchableOpacity} from "react-native";
import React from "react";
import {Feather} from "@expo/vector-icons";

export default function GroupRequestCard({userImage, school, program, message, text, date, style, handleClick, requestId}) {

    return (
        <View
        style={[style, styles.container]}>
            <Image
                style={styles.image}
                source={{uri: userImage}}
            />

            <View style={styles.info}>
                <Text style={styles.text}>{text}</Text>
                <Text style={styles.school}>{message}</Text>
            </View>
            <View>
                <Text style={styles.date}>{date}</Text>
                <TouchableOpacity
                    activeOpacity={0.6}
                    onPress={ () => handleClick(requestId)}
                >
                    <Feather style={styles.checkmark} name={"check"} color={"black"} size={32} />
                </TouchableOpacity>
            </View>
        </View>

    )
}

const styles = StyleSheet.create({
    container: {
        display: "flex",
        flexDirection: "row",
        backgroundColor: "#f8f8f8",
        borderRadius: 20,
        padding: 10,
        alignItems: "center",
        marginTop: 20,
    },
    image: {
        width: 60,
        height: 60,
        borderRadius: 30,
        marginRight: 10,
    },
    info: {
        flex: 1,
        display: "flex",
        marginRight: "auto",
    },
    school: {
        fontSize: 15,
        marginRight: 10,
    },
    program: {
        fontSize: 15,
        fontWeight: "bold",
    },
    text: {
        fontWeight: "bold",
        flexShrink: 1,
        maxWidth: 180
    },
    date: {
        display: "flex",
        justifyContent: "center",
        top: -10
    },
    checkmark: {
        display: "flex",
        alignSelf: "center",
        top: 10
    },
});

