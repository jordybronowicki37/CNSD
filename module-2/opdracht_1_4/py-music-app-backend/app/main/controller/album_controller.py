from flask_restx import Namespace, fields, Resource
from werkzeug.exceptions import BadRequest, NotFound
from ..service import album_service
from ..model.album import Album

api = Namespace(name='albums', path='/rest', description='Album related operations')

# Json field that can be either an integer or null
class Nullable_integer_field(fields.Integer):
    __schema_type__ = ['integer', 'null']
    __schema_example__ = 1

album_model = api.model('Album', {
    'id': Nullable_integer_field(required=False, example=1),
    'name': fields.String(required=True, max_length=100, example='sample_album'),
    'artist': fields.String(required=True, max_length=100, example='sample_artist'),
    'year': fields.Integer(required=True, min=0, max=3000, example=2020)
})

@api.route('/')
class AlbumListEndpoint(Resource):
    @api.doc('get_albums')
    @api.marshal_list_with(album_model)
    def get(self):
        """Get all albums"""
        return album_service.get_all_albums()

    @api.doc('create_album', model=album_model['id'])
    @api.expect(album_model, validate=True)
    @api.response(400, 'Validation Error')
    def post(self):
        """Create a new album"""
        payload = api.payload

        album = Album(name=payload['name'], artist=payload['artist'], year=payload['year'])

        if album_service.same_album_with_different_id_exists(album):
            raise BadRequest('Album already exists')
        return album_service.create_album(album).id

@api.route('/<int:album_id>')
@api.doc(params={'album_id': 'Album ID'})
class AlbumEndpoint(Resource):

    @api.doc('get_album')
    @api.response(404, 'Album not found')
    @api.marshal_with(album_model)
    def get(self, album_id):
        """Get an album my id"""
        album = album_service.find_by_id(album_id)
        if album is None:
            raise NotFound('Album not found')
        return album

    @api.doc('update_album')
    @api.response(400, 'Validation Error')
    @api.response(404, 'Album not found')
    @api.expect(album_model, validate=True)
    @api.marshal_with(album_model)
    def put(self, album_id):
        """Update information of the album with the given id"""
        payload = api.payload

        album = Album(id=album_id, name=payload['name'], artist=payload['artist'], year=payload['year'])

        current_album = album_service.find_by_id(album_id)
        if current_album is None:
            raise NotFound('Album not found')

        if album_service.same_album_with_different_id_exists(album):
            raise BadRequest('Updated album not unique')

        album_service.update_album(album)
        return album

    @api.doc('delete_album')
    @api.response(404, 'Album not found')
    def delete(self, album_id):
        """Delete album with the given id"""
        album = album_service.find_by_id(album_id)
        if album is None:
            raise NotFound('Album not found')
        album_service.delete_album(album)
        return '', 200
