from app.main.service import track_service
from app.main.model.album import Album, Track

def test_add_track_return_track_with_id(session):

    track = Track(albumid=1, tracknr=2, name='track2')
    
    added_track = track_service.add_track(track)
    
    assert isinstance(added_track, Track), f'add_track does not return a Track'
    assert added_track.trackid is not None
    assert isinstance(added_track.trackid, int)
    assert added_track.albumid == 1
    assert added_track.tracknr == 2
    assert added_track.name == 'track2'

    #check that the track is actually in the database
    track_in_database = Track.query.get(added_track.trackid)
    assert track_in_database == added_track

def test_add_track_persist_track_in_database(session):

    track = Track(albumid=1, tracknr=4, name='track2')

    id = track_service.add_track(track).trackid
    
    assert id is not None
    
    #check that the track is actually in the database
    track_in_database = Track.query.get(id)
    assert track_in_database.albumid == 1
    assert track_in_database.tracknr == 4
    assert track_in_database.name == 'track2'