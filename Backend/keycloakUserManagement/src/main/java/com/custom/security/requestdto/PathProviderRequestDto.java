package com.custom.security.requestdto;

/**
 * Interface representing a request DTO for providing paths that should be
 * accessible with or without authentication.
 *
 * Implementations of this interface define an array of paths that should be
 * required in security config.
 */
public interface PathProviderRequestDto {

	/**
	 * Gets an array of paths that should be accessible without authentication.
	 *
	 * @return An array of paths that do not require authentication.
	 */
	String[] getNonAuthenticatedPaths();

}
