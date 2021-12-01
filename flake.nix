{
  description = "peter-comp";
  inputs = { flake-utils.url = "github:numtide/flake-utils"; };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          system = system;
          config = { allowUnfree = true; };
        };
        myjava = pkgs.openjdk;
      in {
        devShell = pkgs.mkShell {
          buildInputs = with pkgs; [ myjava nodejs ];
          nativeBuildInputs = with pkgs; [ openblas gfortran lapack ];
          shellHook = ''
            export JAVA_HOME=${myjava}
                      '';
        };
      });
}
