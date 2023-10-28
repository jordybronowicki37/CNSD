from app.main.service import track_service
from app.main.model.album import Album, Track

def test_find_album_tracks_find_tracks_for_the_given_album(session):

    album1 = Album(id=1, name='sample_album', artist='sample_artist', year=2000)
    album2 = Album(id=2, name='sample_album2', artist='sample_artist', year=2001)
    track_ab1 = Track(albumid=1, tracknr=2, name='track1')
    track_ab2 = Track(albumid=2, tracknr=2, name='track2')
    session.add(album1)
    session.add(album2)
    session.add(track_ab1)
    session.add(track_ab2)
    session.commit()
    
    tracks = track_service.find_album_tracks(album1)
    assert len(tracks) == 1
    assert track_ab1 in tracks
    assert track_ab2 not in tracks
    for track in tracks:
        assert track not in session
