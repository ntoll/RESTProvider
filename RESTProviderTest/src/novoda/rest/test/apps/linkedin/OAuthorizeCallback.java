package novoda.rest.test.apps.linkedin;

public interface OAuthorizeCallback {
	void onRequestTokenSuccess(String token, String secret);

	void onAccessTokenSuccess(String token, String secret);

	void onError(int id, String message);

	void onPreRequest();

	void onPostRequest();
}
