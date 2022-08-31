import React from 'react'
import { StyleSheet, View } from 'react-native'


const GroupDetailsSeparator = ( border ) => (
    <View style={styles.container}>
        <View style={styles.separatorOffset} />
        <View style={styles.separator} />
        <View style={styles.separatorOffset} />
    </View>
)

const styles = StyleSheet.create({
    container: {
        flexDirection: "row",
    },
    separatorOffset: {
        flex: 1,
        flexDirection: "row",
    },
    separator: {
        flex: 8,
        flexDirection: "row",
        borderColor: "#7c7fca",
        borderWidth: 0.7,
    },
})

export default GroupDetailsSeparator