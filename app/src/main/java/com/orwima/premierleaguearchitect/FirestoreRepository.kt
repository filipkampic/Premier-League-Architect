package com.orwima.premierleaguearchitect

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    fun fetchPlayers(
        teamName: String,
        onSuccess: (List<Player>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("players")
            .whereEqualTo("teamName", teamName)
            .get()
            .addOnSuccessListener { result ->

                val players = result.documents
                    .flatMap { document ->
                        val playersArray = document.get("players") as? List<Map<String, Any>> ?: emptyList()

                        playersArray.map { playerData ->
                            Player(
                                name = playerData["name"] as? String ?: "",
                                image = playerData["image"] as? String ?: "",
                                team = teamName,
                                positions = playerData["positions"] as? List<String> ?: emptyList()
                            )
                        }
                    }
                onSuccess(players)
            }
            .addOnFailureListener { e -> onError(e) }
    }

    fun saveLineup(
        oldLineupName: String?,
        newLineupName: String,
        lineup: Lineup,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val lineupRef = db.collection("lineups")
        val lineupWithTimeStamp = lineup.copy(timestamp = System.currentTimeMillis())

        if (oldLineupName != null && oldLineupName == newLineupName) {
            lineupRef.document(newLineupName)
                .set(lineupWithTimeStamp)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e -> onError(e) }
        } else {
            if (!oldLineupName.isNullOrEmpty()) {
                lineupRef.document(oldLineupName)
                    .delete()
                    .addOnSuccessListener {
                        lineupRef
                            .document(newLineupName)
                            .set(lineupWithTimeStamp)
                            .addOnSuccessListener { onSuccess() }
                            .addOnFailureListener { e -> onError(e) }
                    }
                    .addOnFailureListener { e -> onError(e) }
            } else {
                lineupRef.document(newLineupName)
                    .set(lineupWithTimeStamp)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError(e) }
            }
        }
    }

    fun fetchAllLineups(
        onSuccess: (List<Pair<String, Lineup>>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val lineups = result.documents.mapNotNull { document ->
                    val lineupName = document.id
                    val lineupData = document.toObject(Lineup::class.java)
                    if (lineupData != null) lineupName to lineupData else null
                }
                onSuccess(lineups)
            }
            .addOnFailureListener { e -> onError(e) }
    }

    fun fetchTeamLineups(
        teamName: String,
        onSuccess: (List<Pair<String, Lineup>>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups")
            .whereEqualTo("team", teamName)
            .get()
            .addOnSuccessListener { result ->
                val lineups = result.documents.mapNotNull { document ->
                    val lineupName = document.id
                    val lineupData = document.toObject(Lineup::class.java)
                    if (lineupData != null) lineupName to lineupData else null
                }
                onSuccess(lineups)
            }
            .addOnFailureListener { e -> onError(e) }
    }

    fun fetchLineup(
        lineupName: String,
        onSuccess: (Lineup?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups")
            .document(lineupName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val lineup = document.toObject(Lineup::class.java)
                    onSuccess(lineup)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { e -> onError(e) }
    }

    fun deleteLineup(
        lineupName: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups").document(lineupName)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }
}