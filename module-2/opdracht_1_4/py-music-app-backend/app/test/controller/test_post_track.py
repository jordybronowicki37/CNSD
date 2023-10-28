from app.main.controller.track_controller import *
from app.main.model.album import Album, Track
from unittest.mock import patch
import json

@patch('app.main.controller.track_controller.track_service', name='track_service')
@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_create_track_return_track_id(album_service, track_service, client):
    album_service.find_by_id.return_value = Album(id=1, artist='Bob', name='Album1', year='2000')
    track_service.add_track.return_value = Track(albumid=1, trackid=3, name='track', tracknr=1)
    track_service.same_track_with_different_id_exists.return_value = False

    response = client.post('/cddb/rest/1/tracks/', data=json.dumps({'albumid':1, 'tracknr':1, 'name':'track'}), headers={'Content-type': 'application/json'})

    assert response.json == 3
    assert response.status_code == 200

@patch('app.main.controller.track_controller.track_service', name='track_service')
@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_create_track_return_404_if_the_album_is_not_present(album_service, track_service, client):
    album_service.find_by_id.return_value = None

    response = client.post('/cddb/rest/1/tracks/', data=json.dumps({'albumid':1, 'tracknr':1, 'name':'track'}), headers={'Content-type': 'application/json'})

    assert response.status_code == 404
    assert "Album not found" in response.json['message']

@patch('app.main.controller.track_controller.track_service', name='track_service')
@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_create_track_return_400_if_the_same_track_exists(album_service, track_service, client):
    album_service.find_by_id.return_value = Album(id=1, artist='Bob', name='Album1', year='2000')
    track_service.same_track_with_different_id_exists.return_value = True

    response = client.post('/cddb/rest/1/tracks/', data=json.dumps({'albumid':1, 'tracknr':1, 'name':'track'}), headers={'Content-type': 'application/json'})

    assert response.status_code == 400  
    assert "already exists" in response.json['message']

@patch('app.main.controller.track_controller.track_service', name='track_service')
@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_create_track_return_400_if_album_id_is_different_in_path_and_payload(album_service, track_service, client):

    response = client.post('/cddb/rest/2/tracks/', data=json.dumps({'albumid':1, 'tracknr':1, 'name':'track'}), headers={'Content-type': 'application/json'})

    assert response.status_code == 400
    assert "Album id is different" in response.json['message']
    album_service.find_by_id.assert_not_called()

@patch('app.main.controller.track_controller.track_service', name='track_service')
@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_create_track_albumid_is_optional_in_payload(album_service, track_service, client):
    album_service.find_by_id.return_value = Album(id=1, artist='Bob', name='Album1', year='2000')
    track_service.add_track.return_value = Track(albumid=1, trackid=5, name='track', tracknr=1)
    track_service.same_track_with_different_id_exists.return_value = False

    response = client.post('/cddb/rest/2/tracks/', data=json.dumps({'tracknr':1, 'name':'track'}), headers={'Content-type': 'application/json'})

    assert response.status_code == 200
    assert response.json == 5
    same_track_with_different_id_exists_argument =  track_service.same_track_with_different_id_exists.call_args[0][0]
    assert isinstance(same_track_with_different_id_exists_argument, Track)
    assert  same_track_with_different_id_exists_argument.albumid == 2         

@patch('app.main.controller.track_controller.track_service', name='track_service')
@patch('app.main.controller.track_controller.album_service', name='album_service')
def test_create_track_name_is_necessary_in_payload(album_service, track_service, client):

    response = client.post('/cddb/rest/2/tracks/', data=json.dumps({'albumid':1, 'name':'track'}), headers={'Content-type': 'application/json'})

    assert response.status_code == 400     