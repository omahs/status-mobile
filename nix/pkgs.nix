# This file controls the pinned version of nixpkgs we use for our Nix environment
# as well as which versions of package we use, including their overrides.
{ config ? { } }:

let
  inherit (import <nixpkgs> { }) fetchFromGitHub;

  # For testing local version of nixpkgs
  #nixpkgsSrc = (import <nixpkgs> { }).lib.cleanSource "/home/jakubgs/work/nixpkgs";

  # We follow the master branch of official nixpkgs.
  nixpkgsSrc = fetchFromGitHub {
    name = "nixpkgs-source";
    owner = "NixOS";
    repo = "nixpkgs";
    rev = "97747d3209efde533f7b1b28f1be11619f556a06";
    sha256 = "sha256-0+2KzcexiJCB3Il5t7cZAM2RXNRfm5/gMCwhcZJxLuQ=";
    # To get the compressed Nix sha256, use:
    # nix-prefetch-url --unpack https://github.com/${ORG}/nixpkgs/archive/${REV}.tar.gz
  };

  # Status specific configuration defaults
  defaultConfig = import ./config.nix;

  # Override some packages and utilities
  pkgsOverlay = import ./overlay.nix;
in
  # import nixpkgs with a config override
  (import nixpkgsSrc) {
    config = defaultConfig // config;
    overlays = [ pkgsOverlay ];
  }
