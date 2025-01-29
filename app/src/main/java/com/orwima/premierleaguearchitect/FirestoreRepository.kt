package com.orwima.premierleaguearchitect

import com.google.firebase.firestore.FirebaseFirestore

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
        lineupName: String,
        lineup: Lineup,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups")
            .document(lineupName)
            .set(lineup)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }

    fun fetchAllLineups(
        onSuccess: (List<Pair<String, Lineup>>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups")
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
}