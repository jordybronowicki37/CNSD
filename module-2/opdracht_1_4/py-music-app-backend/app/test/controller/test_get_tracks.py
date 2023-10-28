from app.main.controller.track_controller import *
from app.main.model.album import Album, Track
from unittest.mock import patch
import json

@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_get_album_tracks(album_service, client):
    album_service.find_by_id.return_value = None

    response = client.get('/cddb/rest/1/tracks/')

    assert response.status_code == 404
    assert 'message' in response.json
    assert "Album not found" in response.json['message']

@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_get_tracks_for_non_existing_album_should_return_404(album_service, client):
    album_service.find_by_id.return_value = None

    response = client.get('/cddb/rest/1/tracks/')

    album_service.find_by_id.assert_called_with(1)
    assert response.status_code == 404
    assert 'message' in response.json
    assert "Album not found" in response.json['message']

@patch('app.main.controller.track_controller.track_service', name='track_service')
@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_get_tracks_for_album_should_return_track_list(album_service, track_service, client):
    
    album = Album(id=1, artist='Bob', name='Album1', year='2000')
    tracks = [Track(albumid=album.id, trackid=1, name='track', tracknr=1), Track(albumid=album.id, trackid=2, name='track2', tracknr=2)]
    album_service.find_by_id.return_value = album
    track_service.find_album_tracks.return_value = tracks

    response = client.get('/cddb/rest/1/tracks/')

    album_service.find_by_id.assert_called_with(1)
    track_service.find_album_tracks.assert_called_with(album)
    assert response.status_code == 200
    expected_tracks = [{'albumid':1, 'trackid':1, 'tracknr':1, 'name':'track'}, {'albumid':1, 'trackid':2, 'tracknr':2, 'name':'track2'}]
    assert isinstance(response.json, list)
    assert response.json == expected_tracks
