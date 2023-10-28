from flask_restx import Namespace, fields, Resource
from werkzeug.exceptions import BadRequest, NotFound
from werkzeug.exceptions import NotImplemented as NotImplementedException
from app.main.service import album_service, track_service
from app.main.model.track import Track

api = Namespace(name='tracks', path='/rest/<int:albumid>/tracks/', description='Track related operations')

track_model = api.model('Track', {
    'trackid': fields.Integer(required=False, example=1),
    'albumid': fields.Integer(required=False, example=1),
    'name': fields.String(required=True, max_length=100, example='sample_track'),
    'tracknr': fields.Integer(required=True, min=1, example=1)
})

@api.route('/')
@api.doc(params={'albumid': 'Album ID'})
class TrackListEndpoint(Resource):
    @api.doc('get_tracks')
    @api.response(404, 'Album not found')
    @api.marshal_list_with(track_model)
    def get(self, albumid):
        """Get album tracks by album id"""
        # Implement this method:
        # If an album with the given id does not exist return 404 'Album not found' (use the album_service.find_by_id(..) to look up the album)
        # Otherwise find and return all the tracks of the album with the track_service.find_album_tracks(album) method
        raise NotImplementedException('Implement this method!')

    @api.doc('create_track', model=track_model['trackid'])
    @api.expect(track_model, validate=True)
    @api.response(400, 'Validation Error')
    @api.response(404, 'Album not found')
    def post(self, albumid):
        """Add a track to an album"""
        # Implement this method:
        # read the payload of the incoming request from the api.payload object
        # and create a Track object from it
        # - if the album id in the path does not coincide with the album id of the raise BadRequest('Album id is different in path and payload')
        # - if the an album with the given id does not exists raise a NotFound('Album not found') exception
        # - use the track_service.same_track_with_different_id_exists(..) method to check that there exists no other track with same album and number
        #   if such track exists raise a BadRequest(f'Track with this number already exists for this album') exception
        # Otherwise save the track in the database with the track_service.add_track(...) method (to be implemented!) and return the created trackid
        raise NotImplementedException('Implement this method!')

@api.route('/<int:trackid>')
@api.doc(params={'albumid': 'Album ID', 'trackid' : 'Track ID'})
class TrackEndpoint(Resource):

    @api.doc('get_track')
    @api.marshal_with(track_model)
    @api.response(400, 'Validation Error')
    @api.response(404, 'Track not found')
    def get(self, albumid, trackid):
        """Get an track by track id"""
        track = track_service.find_track_by_id(trackid)
        if track is None:
            raise NotFound('Track not found')
        return track

    @api.doc('put_track')
    @api.response(400, 'Validation Error')
    @api.response(404, 'Album or track not found')
    @api.expect(track_model, validate=True)
    @api.marshal_with(track_model)
    def put(self, albumid, trackid):
        """Update track information"""
        payload = api.payload

        # if the album id in the path does not coincide with the album id of the track return BadRequest
        if 'albumid' in payload.keys() and payload['albumid'] != albumid:
            raise BadRequest('Album id is inconsistent')

        album = album_service.find_by_id(albumid)
        track = Track(trackid=trackid, albumid=albumid, tracknr=payload['tracknr'], name=payload['name'])

        if album is None:
            raise NotFound('Album not found')

        track.albumid = albumid
        current_track = track_service.find_track_by_id(trackid)

        if current_track is None:
            raise NotFound('Track not found')

        # check if there is a track with the same album and track number but different id
        if track_service.same_track_with_different_id_exists(track):
            raise BadRequest('Updated track is not unique')

        return track_service.update_track(track)

    @api.doc('delete_track')
    @api.response(404, 'Track not found')
    def delete(self, albumid, trackid):
        """Delete a track"""
        track = track_service.find_track_by_id(trackid)
        if track is None:
            raise NotFound('Track not found')
        track_service.delete_track(track)
