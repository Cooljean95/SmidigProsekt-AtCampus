import {StyleSheet, View, Image, Text, TouchableOpacity} from "react-native";
import React from "react";
import ContentSeparator from "./ContentSeparator";

export default function GroupDetails ({ leftText, rightText }) {

    return (
        <TouchableOpacity
            activeOpacity={0.6}
            style={styles.container}
        >
            {ContentSeparator(0.7)}
            <View style={styles.content}>
                <Text style={{ fontSize: 15, flex: 0.9 }}> { leftText }: </Text>
                <Text numberOfLines={1}  style={{ fontSize: 15, flex: 1.1 }}> { rightText } </Text>
            </View>
        </TouchableOpacity>

    );
}

const styles = StyleSheet.create ({
    container: {
        flex: 1,
        backgroundColor: "white",
    },
    content: {
        flex: 1,
        flexDirection: "row",
        backgroundColor: "white",
        alignItems: "center",
        justifyContent: "space-between",
        margin: 5,
        overflow: "hidden"
    },
})