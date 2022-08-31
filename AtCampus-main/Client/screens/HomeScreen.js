import React, {useEffect, useState} from 'react';
import {FlatList, StyleSheet, Text, View} from "react-native";
import PostPreview from "../components/PostPreview";
import Welcome from "../components/Welcome";
import {SafeAreaView} from "react-native-safe-area-context";
import GroupPreview from "../components/GroupPreview";
import ViewMore from "../components/ViewMore";
import CreateGroup from "../components/CreateGroup";
import {getAllGroups, getAllPostsByGroup, getAllPostsByUser, getAllUserGroups} from "../services/GroupService";
import {useSelector} from "react-redux";

import 'intl';
import 'intl/locale-data/jsonp/en';

function Separator() {
	return <View style={styles.separator}/>
}

function VerticalSeparator() {
	return <View style={styles.verticalSeparator}/>
}

function Groups({groups, navigation}) {
	return (
		<View style={styles.groups}>
			<ViewMore
				text={"My Groups"}
				style={{padding: 20}}
				onPress={() => navigation.navigate("Groups")}
			/>
			
			<FlatList
				contentContainerStyle={styles.groupPreviews}
				data={groups}
				showsHorizontalScrollIndicator={false}
				horizontal={true}
				ListFooterComponent={<CreateGroup
					onPress={() => navigation.navigate("Groups", {
						screen: "Your Groups",
						params: {
							show: true,
						}
					})}
				/>}
				ItemSeparatorComponent={VerticalSeparator}
				renderItem={({item}) =>
					<GroupPreview
						key={item.id}
						image={item.image}
						name={item.name}
						onPress={() => navigation.navigate("Groups", {
							screen: "Group",
							initial: false,
							params: {
								group: item,
							}
						})}
					/>
				}
			/>
			
			<Text style={styles.recent}>Recent Activity</Text>
		</View>
	)
}

function HomeScreen({navigation}) {
	const [groups, setGroups] = useState([]);
	const [posts, setPosts] = useState([]);
	const [date, setDate] = useState(null)

	const { userId } = useSelector(state => state.auth);
	const { username } = useSelector(state => state.auth);

	const getCurrentDate = () => {
		const formatter = new Intl.DateTimeFormat('en-GB', { dateStyle: 'full' })
		return time = formatter.format(new Date())
		//date + '-' + month + '-' + year;//format: dd-mm-yyyy;
	}

	useEffect( () => {
		getAllUserGroups(userId)
			.then(response => {
				const groups = response?.data;
				setGroups(groups)
			})
			.catch((err) => {
				console.log("Failed to get all groups", err)
			})

		getAllPostsByUser(userId)
			.then((postsResponse) => {
				setPosts(postsResponse.data)
			})
			.catch((err) => {
				console.log("Failed in getAllPostsByUser in HomeScreen: " + err)
			})

		navigation.addListener('focus', () => {
			setDate(getCurrentDate())

			getAllUserGroups(userId)
				.then(response => {
					const groups = response?.data;
					setGroups(groups)
					getAllPostsByUser(userId)
						.then((postsResponse) => {
							setPosts(postsResponse.data)
						})
						.catch((err) => {
							console.log("Failed in getAllPostsByUser in HomeScreen: " + err)
						})
				})
				.catch((err) => {
					console.log("Failed to get all groups", err)
				})
		})
	}, [navigation]);


	return (
		<SafeAreaView edges={["top", "left", "right"]} style={styles.container}>
			<View style={styles.welcome}>
				<Welcome
					title={"Good Morning"}
					date={date}
					week={"Week 4"}
					temperature={"25"}
				/>
			</View>
	
			<FlatList
				contentContainerStyle={styles.postPreviews}
				data={posts}
				ListHeaderComponent={<Groups
					groups={groups}
					navigation={navigation}
				/>}
				ItemSeparatorComponent={Separator}
				renderItem={({item}) =>
					<PostPreview
						style={{
							marginHorizontal: 20,
							marginVertical: 2,
						}}
						key={item.id}
						image={item.userEntity?.userProfileImage}
						title={item.title}
						preview={item.body}
						date={item.date}
						onPress={() => navigation.navigate("Groups", {
							screen: "Group Comment",
							initial: true,
							params: {
								post: item,
							}
						})}
					/>
				}
			/>
		</SafeAreaView>
	);
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: "#fff",
	},
	welcome: {
		padding: 20,
		borderBottomWidth: 1,
		borderBottomColor: "#f2f4f5",
	},
	groups: {
		marginBottom: 10,
	},
	groupPreviews: {
		paddingLeft: 20,
		paddingRight: 20,
	},
	postPreviews: {
		paddingBottom: 20,
	},
	separator: {
		height: 10,
	},
	verticalSeparator: {
		width: 10,
	},
	recent: {
		fontSize: 20,
		fontWeight: "bold",
		paddingHorizontal: 20,
		paddingTop: 30,
	}
})

export default HomeScreen;
